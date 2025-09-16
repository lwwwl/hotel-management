package com.example.hotelmanagement.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.MessageTranslate;
import com.example.hotelmanagement.dao.repository.MessageTranslateRepository;
import com.example.hotelmanagement.model.request.GetTranslateResultRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.MessageTranslateService;

import jakarta.annotation.Resource;

@Service
public class MessageTranslateServiceImpl implements MessageTranslateService {

    @Resource
    private MessageTranslateRepository messageTranslateRepository;

    @Override
    public ResponseEntity<?> getTranslateResult(GetTranslateResultRequest request) {
        if (request == null || request.getConversationId() == null || request.getMessageIdList() == null) {
            return ResponseEntity.ok(ApiResponse.error("参数错误"));
        }

        Long conversationId = request.getConversationId();
        List<Long> messageIds = Optional.of(request.getMessageIdList()).orElse(Collections.emptyList());
        if (messageIds.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(Collections.emptyList()));
        }

        List<MessageTranslate> existed = messageTranslateRepository.findByConversationId(conversationId);

        Map<Long, List<MessageTranslate>> messageIdToTranslates = existed.stream()
                .collect(Collectors.groupingBy(MessageTranslate::getMessageId));

        List<MessageTranslate> results = new ArrayList<>();
        Set<Long> missingMessageIds = new HashSet<>();

        for (Long mid : messageIds) {
            List<MessageTranslate> mtList = messageIdToTranslates.get(mid);
            if (mtList != null && !mtList.isEmpty()) {
                results.addAll(mtList);
            } else {
                missingMessageIds.add(mid);
            }
        }

        if (!missingMessageIds.isEmpty()) {
            // TODO: 调用AI翻译服务，按需要的语言生成翻译结果
            // 这里应根据业务约定的目标语言列表，对 missingMessageIds 批量翻译
            // 示例：for each messageId -> create MessageTranslate with language/content from AI

            List<MessageTranslate> toSave = new ArrayList<>();
            for (Long mid : missingMessageIds) {
                MessageTranslate mt = new MessageTranslate();
                mt.setConversationId(conversationId);
                mt.setMessageId(mid);
                // TODO: 设置语言代码，例如 "en" 或从请求上下文推断
                mt.setLanguage("en");
                // TODO: 将AI返回的翻译文本赋值
                mt.setContent("<todo-fill-ai-translation>");
                toSave.add(mt);
            }
            if (!toSave.isEmpty()) {
                List<MessageTranslate> saved = messageTranslateRepository.saveAll(toSave);
                results.addAll(saved);
            }
        }

        return ResponseEntity.ok(ApiResponse.success(results));
    }
}


