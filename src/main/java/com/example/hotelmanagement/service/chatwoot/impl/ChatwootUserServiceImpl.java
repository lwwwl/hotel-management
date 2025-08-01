package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddNewAgentRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserUpdateRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootCreateUserResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserDetailResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserUpdateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserDeleteResponse;
import com.example.hotelmanagement.service.chatwoot.ChatwootUserService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootUserServiceImpl implements ChatwootUserService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootUserServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    // 用户相关操作，只能使用platformAccessToken调用请求
    @Value("${api.chatwoot.platform.access.token}")
    private String platformAccessToken;

    @Override
    public ChatwootCreateUserResponse createUser(ChatwootAddNewAgentRequest request) {
        String apiPath = "/platform/api/v1/users";
        logger.info("调用Chatwoot创建用户接口: {}", apiPath);
        
        try {
            ResponseEntity<ChatwootCreateUserResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootCreateUserResponse.class, platformAccessToken);
            logResponse(response);

            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建用户接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootCreateUserResponse errResponse = new ChatwootCreateUserResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootUserDetailResponse getUserDetail(ChatwootUserDetailRequest request) {
        String apiPath = "/platform/api/v1/users/" + request.getId();
        logger.info("调用Chatwoot获取用户详情接口: {}", apiPath);
        
        try {
            ResponseEntity<ChatwootUserDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootUserDetailResponse.class, platformAccessToken);
            logResponse(response);

            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取用户详情接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootUserDetailResponse errResponse = new ChatwootUserDetailResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootUserUpdateResponse updateUser(ChatwootUserUpdateRequest request) {
        String apiPath = "/platform/api/v1/users/" + request.getId();
        logger.info("调用Chatwoot更新用户接口: {}", apiPath);
        
        try {
            ResponseEntity<ChatwootUserUpdateResponse> response = chatwootHttpClientUtil.patch(apiPath, request, ChatwootUserUpdateResponse.class, platformAccessToken);
            logResponse(response);

            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新用户接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootUserUpdateResponse errResponse = new ChatwootUserUpdateResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootUserDeleteResponse deleteUser(ChatwootUserDeleteRequest request) {
        String apiPath = "/platform/api/v1/users/" + request.getId();
        logger.info("调用Chatwoot删除用户接口: {}", apiPath);
        // 用户关联的 Account, Inbox 等关联关系，会同步删除
        try {
            ResponseEntity<ChatwootUserDeleteResponse> response = chatwootHttpClientUtil.delete(apiPath, null, ChatwootUserDeleteResponse.class, platformAccessToken);
            logResponse(response);

            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot删除用户接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootUserDeleteResponse errResponse = new ChatwootUserDeleteResponse();
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