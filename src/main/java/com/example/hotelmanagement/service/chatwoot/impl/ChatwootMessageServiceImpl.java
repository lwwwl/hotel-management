package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.service.chatwoot.ChatwootMessageService;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootMessageServiceImpl implements ChatwootMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootMessageServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${chatwoot.account.id}")
    private Long accountId;

    @Override
    public ChatwootCreateMessageResponse createMessage(ChatwootCreateMessageRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/messages";
        logger.info("调用Chatwoot创建消息接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootCreateMessageResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootCreateMessageResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建消息接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootCreateMessageResponse errResponse = new ChatwootCreateMessageResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootGetMessagesResponse getMessages(ChatwootGetMessagesRequest request) {
        // todo 额外增加after/before做游标分页

        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/messages";
        logger.info("调用Chatwoot获取消息列表接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootGetMessagesResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootGetMessagesResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取消息列表接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootGetMessagesResponse errResponse = new ChatwootGetMessagesResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }
}
