package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.service.chatwoot.ChatwootMessageService;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootMessageServiceImpl implements ChatwootMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootMessageServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Override
    public ChatwootCreateMessageResponse createMessage(ChatwootCreateMessageRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/messages";
        logger.info("调用Chatwoot创建消息接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootCreateMessageResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootCreateMessageResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建消息接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootGetMessagesResponse getMessages(ChatwootGetMessagesRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/messages";
        logger.info("调用Chatwoot获取消息列表接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootGetMessagesResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootGetMessagesResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取消息列表接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }
}
