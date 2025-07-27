package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactCreateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactUpdateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDetailRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactCreateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactUpdateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactDeleteResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactDetailResponse;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootContactServiceImpl implements ChatwootContactService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Override
    public ChatwootContactCreateResponse createContact(ChatwootContactCreateRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getInboxId() + "/contacts";
        logger.info("调用Chatwoot创建联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactCreateResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootContactCreateResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootContactUpdateResponse updateContact(ChatwootContactUpdateRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/contacts/" + request.getId();
        logger.info("调用Chatwoot更新联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactUpdateResponse> response = chatwootHttpClientUtil.put(apiPath, request, ChatwootContactUpdateResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootContactDeleteResponse deleteContact(ChatwootContactDeleteRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/contacts/" + request.getContactId();
        logger.info("调用Chatwoot删除联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactDeleteResponse> response = chatwootHttpClientUtil.delete(apiPath, null, ChatwootContactDeleteResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot删除联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootContactDetailResponse contactDetail(ChatwootContactDetailRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/contacts/" + request.getContactId();
        logger.info("调用Chatwoot获取联系人详情接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootContactDetailResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取联系人详情接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }
}
