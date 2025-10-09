package com.example.hotelmanagement.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.MessageTranslate;
import com.example.hotelmanagement.dao.repository.MessageTranslateRepository;
import com.example.hotelmanagement.enums.LanguageEnum;
import com.example.hotelmanagement.model.dto.MessageContentInfo;
import com.example.hotelmanagement.model.dto.TranslateResultInfo;
import com.example.hotelmanagement.model.request.GetTranslateResultRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.MessageTranslateService;
import com.example.hotelmanagement.service.ai.AiTranslateService;

import jakarta.annotation.Resource;

@Service
public class MessageTranslateServiceImpl implements MessageTranslateService {

    @Resource
    private MessageTranslateRepository messageTranslateRepository;

    @Resource
    private AiTranslateService aiTranslateService;

    @Override
    public ResponseEntity<?> getTranslateResult(GetTranslateResultRequest request) {
        if (request == null || request.getConversationId() == null || request.getMessages() == null || request.getLanguage() == null
                || !LanguageEnum.isValidCode(request.getLanguage())) {
            return ResponseEntity.ok(ApiResponse.error("参数错误"));
        }

        Long conversationId = request.getConversationId();
        String languageCode = request.getLanguage();
        List<MessageContentInfo> messages = Optional.of(request.getMessages()).orElse(Collections.emptyList());
        if (messages.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(Collections.emptyList()));
        }

        List<Long> messageIds = messages.stream().map(MessageContentInfo::getMessageId).collect(Collectors.toList());

        // 根据conversationId + messageIdList + 语言查询已存在的翻译结果
        List<MessageTranslate> existed = messageTranslateRepository.findByConversationIdAndMessageIdInAndLanguage(conversationId, messageIds, languageCode);

        Map<Long, String> messageIdToTranslatedContent = existed.stream()
                .collect(Collectors.toMap(MessageTranslate::getMessageId, MessageTranslate::getContent, (existing, replacement) -> existing));

        List<TranslateResultInfo> results = new ArrayList<>();
        List<MessageContentInfo> pendingTranslateList = new ArrayList<>();

        // 匹配messageId，找到已存在的翻译结果
        for (MessageContentInfo message : messages) {
            String translatedContent = messageIdToTranslatedContent.get(message.getMessageId());
            if (translatedContent != null) {
                results.add(new TranslateResultInfo(message.getMessageId(), translatedContent));
            } else {
                pendingTranslateList.add(message);
            }
        }

        // 对于匹配不到的message：
        if (!pendingTranslateList.isEmpty()) {
            List<TranslateResultInfo> translatedResults;
            try {
                translatedResults = aiTranslateService.batchTranslate(pendingTranslateList,
                        LanguageEnum.fromCode(request.getLanguage()));
            } catch (Exception e) {
                return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
            }
            List<MessageTranslate> newTranslations = new ArrayList<>();
            for (TranslateResultInfo result : translatedResults) {
                MessageTranslate mt = new MessageTranslate();
                mt.setConversationId(request.getConversationId());
                mt.setMessageId(result.getMessageId());
                mt.setLanguage(languageCode);
                mt.setContent(result.getResult());
                mt.setCreateTime(new Timestamp(System.currentTimeMillis()));
                mt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                newTranslations.add(mt);
            }
            // 逐条保存新翻译，忽略唯一约束冲突
            for (MessageTranslate translation : newTranslations) {
                try {
                    messageTranslateRepository.save(translation);
                } catch (DataIntegrityViolationException e) {
                    // 并发场景下，另一线程可能已成功插入，忽略此异常并继续
                }
            }
            results.addAll(translatedResults);
        }

        return ResponseEntity.ok(ApiResponse.success(results));
    }
}


