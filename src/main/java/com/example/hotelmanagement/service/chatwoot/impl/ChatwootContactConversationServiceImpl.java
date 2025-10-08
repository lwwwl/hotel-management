package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.bo.ChatwootGuestCreateConversationRespBO;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactConversationService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ChatwootContactConversationServiceImpl implements ChatwootContactConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactConversationServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value(value = "${api.chatwoot.inbox.identifier.token}")
    private String inboxIdentifier;

    @Override
    public GuestChatwootConversationListResponse getConversationList(GuestChatwootConversationListRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations", inboxIdentifier, request.getContactIdentifier());
        logger.info("访客获取会话列表: {}", apiPath);
        try {
            ResponseEntity<List> response = chatwootHttpClientUtil.get(apiPath, null, null, List.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            GuestChatwootConversationListResponse resp = new GuestChatwootConversationListResponse();
            // 这里需要将List<Map>转为List<ChatwootConversationBO>，可根据实际需要做转换
            resp.setConversations(response.getBody());
            return resp;
        } catch (Exception e) {
            logger.error("访客获取会话列表失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootConversationListResponse errResp = new GuestChatwootConversationListResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }

    @Override
    public ChatwootGuestCreateConversationRespBO createConversation(GuestChatwootCreateConversationRequest request) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations", inboxIdentifier, request.getContactIdentifier());
        logger.info("访客创建会话: {}", apiPath);
        try {
            ResponseEntity<Map> response = chatwootHttpClientUtil.post(apiPath, request, Map.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return parseCreateConversationResponse(response);
        } catch (Exception e) {
            logger.error("访客创建会话失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public GuestChatwootConversationDetailResponse getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d", inboxIdentifier, request.getContactIdentifier(), request.getConversationId());
        logger.info("访客获取会话详情: {}", apiPath);
        try {
            ResponseEntity<GuestChatwootConversationDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, GuestChatwootConversationDetailResponse.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("访客获取会话详情失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootConversationDetailResponse errResp = new GuestChatwootConversationDetailResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }

    @Override
    public GuestChatwootUpdateLastSeenResponse updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/update_last_seen", inboxIdentifier, request.getContactIdentifier(), request.getConversationId());
        logger.info("访客更新会话已读: {}", apiPath);
        try {
            ResponseEntity<GuestChatwootUpdateLastSeenResponse> response = chatwootHttpClientUtil.post(apiPath, null, GuestChatwootUpdateLastSeenResponse.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("访客更新会话已读失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootUpdateLastSeenResponse errResp = new GuestChatwootUpdateLastSeenResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }

    private ChatwootGuestCreateConversationRespBO parseCreateConversationResponse(ResponseEntity<Map> response) {
        Map<String, Object> body = response.getBody();
        if (body == null || !body.containsKey("id")) {
            logger.warn("Chatwoot响应body为空或不包含id");
            return null;
        }

        Object idObj = body.get("id");
        if (!(idObj instanceof Number)) {
            logger.warn("Chatwoot响应id格式不正确");
            return null;
        }

        Long conversationId = ((Number) idObj).longValue();
        ChatwootGuestCreateConversationRespBO respBO = new ChatwootGuestCreateConversationRespBO();
        respBO.setConversationId(conversationId);
        return respBO;
    }
}
