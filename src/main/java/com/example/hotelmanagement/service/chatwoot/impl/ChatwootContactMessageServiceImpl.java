package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.bo.ChatwootMessageBO;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactMessageService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatwootContactMessageServiceImpl implements ChatwootContactMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactMessageServiceImpl.class);

    @Value(value = "${api.chatwoot.inbox.identifier.token}")
    private String inboxIdentifier;

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Override
    public Map<String, Object> getMessages(GuestChatwootGetMessagesRequest request) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/messages", inboxIdentifier, request.getContactIdentifier(), request.getConversationId());
        logger.info("访客获取消息列表: {}", apiPath);
        try {
            ResponseEntity<List> response = chatwootHttpClientUtil.get(apiPath, null, null, List.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return Map.of("messages", response.getBody());
        } catch (Exception e) {
            logger.error("访客获取消息列表失败: {}, error: {}", apiPath, e.getMessage(), e);
            Map<String, Object> errRespMap = new HashMap<>();
            errRespMap.put("error", e.getMessage());
            return errRespMap;
        }
    }

    @Override
    public Map<String, Object> sendMessage(GuestChatwootSendMessageRequest request) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/messages", inboxIdentifier, request.getContactIdentifier(), request.getConversationId());
        logger.info("访客发送消息: {}", apiPath);
        try {
            ResponseEntity<Map> response = chatwootHttpClientUtil.post(apiPath, request, Map.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("访客发送消息失败: {}, error: {}", apiPath, e.getMessage(), e);
            Map<String, Object> errRespMap = new HashMap<>();
            errRespMap.put("error", e.getMessage());
            return errRespMap;
        }
    }

//    @Override
//    public GuestChatwootUpdateMessageResponse updateMessage(GuestChatwootUpdateMessageRequest request) {
//        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/messages/%s", inboxIdentifier, request.getContactIdentifier(), request.getConversationId(), request.getMessageId());
//        logger.info("访客更新消息: {}", apiPath);
//        try {
//            ResponseEntity<GuestChatwootUpdateMessageResponse> response = chatwootHttpClientUtil.patch(apiPath, request, GuestChatwootUpdateMessageResponse.class, null);
//            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
//            return response.getBody();
//        } catch (Exception e) {
//            logger.error("访客更新消息失败: {}, error: {}", apiPath, e.getMessage(), e);
//            GuestChatwootUpdateMessageResponse errResp = new GuestChatwootUpdateMessageResponse();
//            errResp.setError(e.getMessage());
//            return errResp;
//        }
//    }
}
