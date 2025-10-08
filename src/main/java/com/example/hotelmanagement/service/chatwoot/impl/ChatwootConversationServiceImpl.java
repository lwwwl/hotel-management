package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.service.chatwoot.ChatwootConversationService;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.HashMap;

@Service
public class ChatwootConversationServiceImpl implements ChatwootConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootConversationServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${chatwoot.account.id}")
    private Long accountId;
    @Value("${chatwoot.inbox.id}")
    private Long inboxId;

    @Override
    public ChatwootConversationCountResponse getConversationCount(ChatwootConversationCountRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/meta";
        logger.info("调用Chatwoot获取会话统计接口: {}", apiPath);
        try {
            Map<String, String> queryParams = new HashMap<>();
            if (!StringUtils.hasText(request.getStatus())) {
                queryParams.put("status", request.getStatus());
            }
            if (request.getInboxId() != null) {
                queryParams.put("inbox_id", inboxId.toString());
            }
            ResponseEntity<ChatwootConversationCountResponse> response = chatwootHttpClientUtil.get(apiPath, null, queryParams, ChatwootConversationCountResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取会话统计接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootConversationCountResponse errResponse = new ChatwootConversationCountResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootConversationListResponse getConversationList(ChatwootConversationListRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations";
        logger.info("调用Chatwoot获取会话列表接口: {}", apiPath);
        try {
            

            Map<String, String> queryParams = new HashMap<>();
            if (request.getLabel() != null) {
                queryParams.put("labels", request.getLabel());
            }
            queryParams.put("inbox_id", inboxId.toString());
            if (request.getAssigneeType() != null) {
                queryParams.put("assignee_type", request.getAssigneeType());
            }
            if (StringUtils.hasText(request.getStatus())) {
                queryParams.put("status", request.getStatus());
            }
            ResponseEntity<ChatwootConversationListResponse> response = chatwootHttpClientUtil.get(apiPath, null, queryParams, ChatwootConversationListResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取会话列表接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootConversationListResponse errResponse = new ChatwootConversationListResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootCreateConversationResponse createConversation(ChatwootCreateConversationRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations";
        logger.info("调用Chatwoot创建会话接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootCreateConversationResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootCreateConversationResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot创建会话接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootCreateConversationResponse errResponse = new ChatwootCreateConversationResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootUpdateConversationResponse updateConversation(ChatwootUpdateConversationRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId();
        logger.info("调用Chatwoot更新会话接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootUpdateConversationResponse> response = chatwootHttpClientUtil.patch(apiPath, request, ChatwootUpdateConversationResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新会话接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootUpdateConversationResponse errResponse = new ChatwootUpdateConversationResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootConversationDetailResponse getConversationDetail(ChatwootConversationDetailRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId();
        logger.info("调用Chatwoot获取会话详情接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootConversationDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootConversationDetailResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取会话详情接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootConversationDetailResponse errResponse = new ChatwootConversationDetailResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootAddConversationLabelResponse addConversationLabel(ChatwootAddConversationLabelRequest request) {
        String apiPath = "/api/v1/accounts/" + request.getAccountId() + "/conversations/" + request.getConversationId() + "/labels";
        logger.info("调用Chatwoot添加会话标签接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootAddConversationLabelResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootAddConversationLabelResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot添加会话标签接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootAddConversationLabelResponse errResponse = new ChatwootAddConversationLabelResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public boolean assignConversation(ChatwootAssignConversationRequest request, String userAccessToken, Long chatwootUserId) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/assignments";
        logger.info("调用Chatwoot分配会话接口: {}", apiPath);
        try {
            ResponseEntity<Map> response = chatwootHttpClientUtil.post(apiPath, request, Map.class, userAccessToken);
            logResponse(response);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("调用Chatwoot分配会话接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public ChatwootUpdateConversationCustomAttributesResponse updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/custom_attributes";
        logger.info("调用Chatwoot更新会话自定义属性接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootUpdateConversationCustomAttributesResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootUpdateConversationCustomAttributesResponse.class, request.getAccessToken());
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新会话自定义属性接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootUpdateConversationCustomAttributesResponse errResponse = new ChatwootUpdateConversationCustomAttributesResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public boolean toggleConversationStatus(ChatwootToggleConversationStatusRequest request, String userAccessToken) {
        String apiPath = "/api/v1/accounts/" + accountId + "/conversations/" + request.getConversationId() + "/toggle_status";
        logger.info("调用Chatwoot切换会话状态接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootToggleConversationStatusResponse> response = chatwootHttpClientUtil.post(apiPath, request, ChatwootToggleConversationStatusResponse.class, userAccessToken);
            logResponse(response);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("调用Chatwoot切换会话状态接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return false;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }
}
