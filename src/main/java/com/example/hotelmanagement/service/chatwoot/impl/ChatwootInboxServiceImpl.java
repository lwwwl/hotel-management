package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootInboxMemberRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToInboxResponse;
import com.example.hotelmanagement.service.chatwoot.ChatwootInboxService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootInboxServiceImpl implements ChatwootInboxService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootInboxServiceImpl.class);
    private final ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${chatwoot.account.id}")
    private Long accountId;

    @Autowired
    public ChatwootInboxServiceImpl(ChatwootHttpClientUtil chatwootHttpClientUtil) {
        this.chatwootHttpClientUtil = chatwootHttpClientUtil;
    }

    @Override
    public ChatwootAddUserToInboxResponse addUserToInbox(ChatwootInboxMemberRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/inbox_members";
        logger.info("调用Chatwoot添加用户到收件箱接口: {}", apiPath);
        
        try {
            ResponseEntity<ChatwootAddUserToInboxResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootAddUserToInboxResponse.class, request.getAccessToken());
            logResponse(response);
            
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot添加用户到收件箱接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootAddUserToInboxResponse errResponse = new ChatwootAddUserToInboxResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot收件箱接口返回 - statusCode: {}, message: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? ((ApiResponse) response.getBody()).getMessage() : "null");
    }
}
