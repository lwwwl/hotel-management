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

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatwootMessageServiceImpl implements ChatwootMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootMessageServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${chatwoot.account.id}")
    private Long accountId;

    @Override
    public Map<String, Object> createMessage(ChatwootCreateMessageRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/messages";
        logger.info("调用Chatwoot创建消息接口: {}", apiPath);
        try {
            ResponseEntity<Map> response = chatwootHttpClientUtil.post(apiPath, request, Map.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建消息接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            Map<String, Object> errResponse = new HashMap<>();
            errResponse.put("error", e.getMessage());
            return errResponse;
        }
    }

    @Override
    public Map<String, Object> getMessages(ChatwootGetMessagesRequest request) {
        StringBuilder apiPath = new StringBuilder("/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/messages");
        if (request.getBefore() != null || request.getAfter() != null) {
            apiPath.append("?");
        }
        if  (request.getBefore() != null) {
            apiPath.append("before=").append(request.getBefore());
        } else if  (request.getAfter() != null) {
            apiPath.append("after=").append(request.getAfter());
        }
        logger.info("调用Chatwoot获取消息列表接口: {}", apiPath);
        try {
            ResponseEntity<Map> response = chatwootHttpClientUtil.get(apiPath.toString(), null, null, Map.class, request.getAccessToken());
            logger.info("Chatwoot获取消息列表接口返回: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取消息列表接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            Map<String, Object> errResponse = new HashMap<>();
            errResponse.put("error", e.getMessage());
            return errResponse;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }
}
