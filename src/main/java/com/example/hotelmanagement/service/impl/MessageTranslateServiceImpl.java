package com.example.hotelmanagement.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.MessageTranslate;
import com.example.hotelmanagement.dao.repository.MessageTranslateRepository;
import com.example.hotelmanagement.enums.ChatwootMessageType;
import com.example.hotelmanagement.model.request.GetTranslateResultRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootGetMessagesRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootGetMessagesResponse;
import com.example.hotelmanagement.model.bo.ChatwootMessageBO;
import com.example.hotelmanagement.service.MessageTranslateService;
import com.example.hotelmanagement.service.ChatwootUserFacadeService;
import com.example.hotelmanagement.util.UserContext;

import jakarta.annotation.Resource;

@Service
public class MessageTranslateServiceImpl implements MessageTranslateService {

    @Resource
    private MessageTranslateRepository messageTranslateRepository;

    @Resource
    private ChatwootUserFacadeService chatwootUserFacadeService;

    @Override
    public ResponseEntity<?> getTranslateResult(GetTranslateResultRequest request) {
        if (request == null || request.getConversationId() == null || request.getMessageIdList() == null || request.getLanguage() == null) {
            return ResponseEntity.ok(ApiResponse.error("参数错误"));
        }

        Long conversationId = request.getConversationId();
        String languageCode = request.getLanguage().getCode();
        List<Long> messageIds = Optional.of(request.getMessageIdList()).orElse(Collections.emptyList());
        if (messageIds.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(Collections.emptyList()));
        }

        // 根据conversationId + messageIdList + 语言查询已存在的翻译结果
        List<MessageTranslate> existed = messageTranslateRepository.findByConversationIdAndMessageIdInAndLanguage(conversationId, messageIds, languageCode);

        Map<Long, MessageTranslate> messageIdToTranslate = existed.stream()
                .collect(Collectors.toMap(MessageTranslate::getMessageId, mt -> mt, (existing, replacement) -> existing));

        List<MessageTranslate> results = new ArrayList<>();
        Set<Long> missingMessageIds = new HashSet<>();

        // 匹配messageId，找到已存在的翻译结果
        for (Long mid : messageIds) {
            MessageTranslate mt = messageIdToTranslate.get(mid);
            if (mt != null) {
                results.add(mt);
            } else {
                missingMessageIds.add(mid);
            }
        }

        // 对于匹配不到的messageId：
        if (!missingMessageIds.isEmpty()) {
            // 第一步：分页获取会话内全量消息（仅保留正常消息的 content，丢弃系统消息），避免大对象保留以节省内存
            Map<Long, String> allMessageContents = fetchAllConversationMessageContents(conversationId);

            // 第二步：根据未命中ID列表，从全量消息中匹配出待翻译的原文数据
            List<Map<String, Object>> pendingTranslateList = new ArrayList<>();
            for (Long missId : missingMessageIds) {
                String content = allMessageContents.get(missId);
                if (content != null && !content.isEmpty()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("messageId", missId);
                    item.put("content", content);
                    pendingTranslateList.add(item);
                }
            }

            // 第三步：构造用于多语言翻译的 prompt，并调用 AI 翻译（此处先不实现，只保留 TODO）
            // TODO: 使用 pendingTranslateList 组装 prompt，一次性请求 AI 获得所有翻译结果；
            // TODO: 将返回的结构化结果按 messageId 拆分并保存至 message_translate 表；
            // 注意：本次实现暂不调用 AI，也不保存占位数据。
        }

        return ResponseEntity.ok(ApiResponse.success(results));
    }

    /**
     * 分页获取指定会话的所有消息内容，仅保留普通消息类型的 content 字段。
     * 首次请求：before=null, after=null，返回最近的消息页；
     * 翻页规则：将 before 设为当前页第一条消息的 id，向前获取更早的消息；
     * 终止条件：当 payload 为空或为 null 时，停止。
     */
    private Map<Long, String> fetchAllConversationMessageContents(Long conversationId) {
        Map<Long, String> idToContent = new HashMap<>();

        Integer before = null;
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return idToContent;
        }

        while (true) {
            ChatwootGetMessagesRequest req = new ChatwootGetMessagesRequest();
            req.setAccessToken(null); // 由 Facade 内部补全
            req.setAfter(null);
            req.setBefore(before);
            req.setConversationId(conversationId);

            ResponseEntity<?> respEntity = chatwootUserFacadeService.getMessages(req, userId);
            if (respEntity == null || respEntity.getBody() == null) {
                break;
            }

            Object body = respEntity.getBody();
            if (!(body instanceof ChatwootGetMessagesResponse)) {
                break;
            }
            ChatwootGetMessagesResponse resp = (ChatwootGetMessagesResponse) body;

            List<ChatwootMessageBO> payload = resp.getPayload();
            if (payload == null || payload.isEmpty()) {
                break;
            }

            // 只取普通消息类型，且内容非空，保存到映射中
            for (ChatwootMessageBO msg : payload) {
                if (msg == null) { continue; }
                Integer type = msg.getMessageType();
                String content = msg.getContent();
                Long mid = msg.getId();
                if (ChatwootMessageType.isNormal(type) && mid != null && content != null && !content.isEmpty()) {
                    idToContent.put(mid, content);
                }
            }

            // 下一页：使用本页第一条消息的 id 作为 before
            ChatwootMessageBO first = payload.get(0);
            if (first == null || first.getId() == null) {
                break;
            }
            try {
                before = Math.toIntExact(first.getId());
            } catch (ArithmeticException ex) {
                // id 超出 Integer 范围时，停止分页以避免异常
                break;
            }
        }

        return idToContent;
    }
}


