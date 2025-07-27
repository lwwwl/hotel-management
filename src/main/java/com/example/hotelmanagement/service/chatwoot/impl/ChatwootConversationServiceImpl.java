package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.service.chatwoot.ChatwootConversationService;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootConversationServiceImpl implements ChatwootConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootConversationServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Override
    public ChatwootConversationCountResponse getConversationCount(ChatwootConversationCountRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/meta";
        logger.info("调用Chatwoot获取会话统计接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootConversationCountResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootConversationCountResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取会话统计接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootConversationListResponse getConversationList(ChatwootConversationListRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations";
        logger.info("调用Chatwoot获取会话列表接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootConversationListResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootConversationListResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取会话列表接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootCreateConversationResponse createConversation(ChatwootCreateConversationRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getInboxId() + "/conversations";
        logger.info("调用Chatwoot创建会话接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootCreateConversationResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootCreateConversationResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建会话接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootUpdateConversationResponse updateConversation(ChatwootUpdateConversationRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId();
        logger.info("调用Chatwoot更新会话接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootUpdateConversationResponse> response = chatwootHttpClientUtil.patch(apiPath, request, ChatwootUpdateConversationResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新会话接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootConversationDetailResponse getConversationDetail(ChatwootConversationDetailRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId();
        logger.info("调用Chatwoot获取会话详情接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootConversationDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootConversationDetailResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取会话详情接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootAddConversationLabelResponse addConversationLabel(ChatwootAddConversationLabelRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/labels";
        logger.info("调用Chatwoot添加会话标签接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootAddConversationLabelResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootAddConversationLabelResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot添加会话标签接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootAssignConversationResponse assignConversation(ChatwootAssignConversationRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/assignments";
        logger.info("调用Chatwoot分配会话接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootAssignConversationResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootAssignConversationResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot分配会话接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootUpdateConversationCustomAttributesResponse updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/custom_attributes";
        logger.info("调用Chatwoot更新会话自定义属性接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootUpdateConversationCustomAttributesResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootUpdateConversationCustomAttributesResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新会话自定义属性接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootToggleConversationStatusResponse toggleConversationStatus(ChatwootToggleConversationStatusRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/toggle_status";
        logger.info("调用Chatwoot切换会话状态接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootToggleConversationStatusResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootToggleConversationStatusResponse.class);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot切换会话状态接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }
}
