package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactMessageService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatwootContactContactMessageServiceImpl implements ChatwootContactMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactContactMessageServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Override
    public GuestChatwootGetMessagesResponse getMessages(GuestChatwootGetMessagesRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/messages", request.getInboxIdentifier(), request.getContactIdentifier(), request.getConversationId());
        logger.info("访客获取消息列表: {}", apiPath);
        try {
            ResponseEntity<List> response = chatwootHttpClientUtil.get(apiPath, null, null, List.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            GuestChatwootGetMessagesResponse resp = new GuestChatwootGetMessagesResponse();
            resp.setMessages(response.getBody());
            return resp;
        } catch (Exception e) {
            logger.error("访客获取消息列表失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootGetMessagesResponse errResp = new GuestChatwootGetMessagesResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }

    @Override
    public GuestChatwootSendMessageResponse sendMessage(GuestChatwootSendMessageRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/messages", request.getInboxIdentifier(), request.getContactIdentifier(), request.getConversationId());
        logger.info("访客发送消息: {}", apiPath);
        try {
            ResponseEntity<GuestChatwootSendMessageResponse> response = chatwootHttpClientUtil.post(apiPath, request, GuestChatwootSendMessageResponse.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("访客发送消息失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootSendMessageResponse errResp = new GuestChatwootSendMessageResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }

    @Override
    public GuestChatwootUpdateMessageResponse updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/messages/%s", request.getInboxIdentifier(), request.getContactIdentifier(), request.getConversationId(), request.getMessageId());
        logger.info("访客更新消息: {}", apiPath);
        try {
            ResponseEntity<GuestChatwootUpdateMessageResponse> response = chatwootHttpClientUtil.patch(apiPath, request, GuestChatwootUpdateMessageResponse.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("访客更新消息失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootUpdateMessageResponse errResp = new GuestChatwootUpdateMessageResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }
}
