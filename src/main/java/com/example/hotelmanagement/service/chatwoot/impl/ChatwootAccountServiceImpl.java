package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAccountUserRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToAccountResponse;
import com.example.hotelmanagement.service.chatwoot.ChatwootAccountService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootAccountServiceImpl implements ChatwootAccountService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootAccountServiceImpl.class);
    private final ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Autowired
    public ChatwootAccountServiceImpl(ChatwootHttpClientUtil chatwootHttpClientUtil) {
        this.chatwootHttpClientUtil = chatwootHttpClientUtil;
    }

    @Override
    public ChatwootAddUserToAccountResponse addUserToAccount(ChatwootAccountUserRequest request) {
        String apiPath = "/platform/api/v1/accounts/" + request.getAccountId() + "/account_users";
        logger.info("调用Chatwoot添加用户到账户接口: {}", apiPath);
        
        try {
            ResponseEntity<ChatwootAddUserToAccountResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootAddUserToAccountResponse.class);
            logResponse(response);

            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot添加用户到账户接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot账户接口返回 - response: {}", response.getBody());
    }
}
