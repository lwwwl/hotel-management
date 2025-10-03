package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddLabelRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddLabelResponse;
import com.example.hotelmanagement.service.chatwoot.ChatwootLabelService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootLabelServiceImpl implements ChatwootLabelService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootLabelServiceImpl.class);
    private final ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${api.chatwoot.administrator.access.token}")
    private String apiAccessToken;

    @Value("${chatwoot.account.id}")
    private Long accountId;

    @Autowired
    public ChatwootLabelServiceImpl(ChatwootHttpClientUtil chatwootHttpClientUtil) {
        this.chatwootHttpClientUtil = chatwootHttpClientUtil;
    }

    @Override
    public Boolean addLabel(ChatwootAddLabelRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getContactId() + "/labels";
        logger.info("调用Chatwoot添加标签接口: {}", apiPath);
        
        try {
            ResponseEntity<ChatwootAddLabelResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootAddLabelResponse.class, apiAccessToken);
            logResponse(response);

            return response.getBody() != null && response.getBody().getPayload() != null;
        } catch (Exception e) {
            logger.error("调用Chatwoot添加标签接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return false;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot标签接口返回 - response: {}", response.getBody());
    }
}
