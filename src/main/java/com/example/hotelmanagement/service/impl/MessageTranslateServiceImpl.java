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
            try {
                // 首次尝试批量保存
                messageTranslateRepository.saveAll(newTranslations);
            } catch (DataIntegrityViolationException e) {
                // 捕获唯一约束冲突异常。这通常发生在并发请求翻译相同消息时。
                // 为了处理这种情况，我们筛选出数据库中已存在的记录，
                // 然后仅对新记录进行第二次批量保存尝试。
                List<Long> messageIdsToCheck = newTranslations.stream()
                        .map(MessageTranslate::getMessageId)
                        .collect(Collectors.toList());

                // 重新查询数据库，找出哪些消息的翻译已经存在
                List<MessageTranslate> existingTranslations = messageTranslateRepository.findByConversationIdAndMessageIdInAndLanguage(
                        conversationId, messageIdsToCheck, languageCode);

                Set<Long> existingMessageIds = existingTranslations.stream()
                        .map(MessageTranslate::getMessageId)
                        .collect(Collectors.toSet());

                // 过滤出尚未保存的翻译记录
                List<MessageTranslate> translationsToSave = newTranslations.stream()
                        .filter(mt -> !existingMessageIds.contains(mt.getMessageId()))
                        .collect(Collectors.toList());

                // 如果还有未保存的记录，则进行第二次批量保存
                if (!translationsToSave.isEmpty()) {
                    messageTranslateRepository.saveAll(translationsToSave);
                }
            }
            results.addAll(translatedResults);
        }

        return ResponseEntity.ok(ApiResponse.success(results));
    }
}


