package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactConversationService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatwootContactContactConversationServiceImpl implements ChatwootContactConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactContactConversationServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Override
    public GuestChatwootConversationListResponse getConversationList(GuestChatwootConversationListRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations", request.getInboxIdentifier(), request.getContactIdentifier());
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
    public GuestChatwootCreateConversationResponse createConversation(GuestChatwootCreateConversationRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations", request.getInboxIdentifier(), request.getContactIdentifier());
        logger.info("访客创建会话: {}", apiPath);
        try {
            ResponseEntity<GuestChatwootCreateConversationResponse> response = chatwootHttpClientUtil.post(apiPath, request, GuestChatwootCreateConversationResponse.class, null);
            logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("访客创建会话失败: {}, error: {}", apiPath, e.getMessage(), e);
            GuestChatwootCreateConversationResponse errResp = new GuestChatwootCreateConversationResponse();
            errResp.setError(e.getMessage());
            return errResp;
        }
    }

    @Override
    public GuestChatwootConversationDetailResponse getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId) {
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d", request.getInboxIdentifier(), request.getContactIdentifier(), request.getConversationId());
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
        String apiPath = String.format("/public/api/v1/inboxes/%s/contacts/%s/conversations/%d/update_last_seen", request.getInboxIdentifier(), request.getContactIdentifier(), request.getConversationId());
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
}
