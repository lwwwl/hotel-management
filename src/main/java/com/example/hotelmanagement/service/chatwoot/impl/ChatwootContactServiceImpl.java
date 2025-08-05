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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootContactServiceImpl implements ChatwootContactService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${api.chatwoot.access.token}")
    private String apiAccessToken;

    @Value("${chatwoot.account.id}")
    private Long accountId;

    /**
     * Chatwoot侧创建contact唯一性检测：Email, Identifier, Phone number
     * <br>Identifier传随机字符串，其余传null，可以跳过检测。
     * @param request
     * @return
     */
    @Override
    public ChatwootContactCreateResponse createContact(ChatwootContactCreateRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts";
        logger.info("调用Chatwoot创建联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactCreateResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootContactCreateResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactCreateResponse errResponse = new ChatwootContactCreateResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootContactUpdateResponse updateContact(ChatwootContactUpdateRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getId();
        logger.info("调用Chatwoot更新联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactUpdateResponse> response = chatwootHttpClientUtil.put(apiPath, request, ChatwootContactUpdateResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactUpdateResponse errResponse = new ChatwootContactUpdateResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootContactDeleteResponse deleteContact(ChatwootContactDeleteRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getContactId();
        logger.info("调用Chatwoot删除联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactDeleteResponse> response = chatwootHttpClientUtil.delete(apiPath, null, ChatwootContactDeleteResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot删除联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactDeleteResponse errResponse = new ChatwootContactDeleteResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootContactDetailResponse contactDetail(ChatwootContactDetailRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getContactId();
        logger.info("调用Chatwoot获取联系人详情接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootContactDetailResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取联系人详情接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactDetailResponse errResponse = new ChatwootContactDetailResponse();
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
