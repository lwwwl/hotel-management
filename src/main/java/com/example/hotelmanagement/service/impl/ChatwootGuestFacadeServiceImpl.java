package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.service.ChatwootGuestFacadeService;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactService;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactConversationService;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactMessageService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ChatwootGuestFacadeServiceImpl implements ChatwootGuestFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootGuestFacadeServiceImpl.class);

    @Resource
    private ChatwootContactService chatwootContactService;

    @Resource
    private ChatwootContactConversationService chatwootContactConversationService;

    @Resource
    private ChatwootContactMessageService chatwootContactMessageService;

    @Override
    public ChatwootContactDetailBO createContact(ChatwootContactCreateRequest request) {
        ChatwootContactCreateResponse contactCreateResponse = chatwootContactService.createContact(request);
        if (contactCreateResponse == null ||
                StringUtils.hasText(contactCreateResponse.getError())) {
            String errMsg = contactCreateResponse == null ? "" :  contactCreateResponse.getError();
            logger.error("创建联系人失败: {}",  errMsg);
            return null;
        }
        // 避免空指针异常
        if (contactCreateResponse.getPayload() == null) {
            return null;
        }
        return contactCreateResponse.getPayload().getFirst();
    }

    @Override
    public ChatwootContactDetailBO updateContact(ChatwootContactUpdateRequest request) {
        return null;
    }

    @Override
    public Boolean deleteContact(ChatwootContactDeleteRequest request, Long guestId) {
        ChatwootContactDeleteResponse chatwootContactDeleteResponse = chatwootContactService.deleteContact(request);
        if (StringUtils.hasText(chatwootContactDeleteResponse.getError())) {
            logger.error(chatwootContactDeleteResponse.getError());
            return false;
        }
        return true;
    }

    @Override
    public ChatwootContactDetailBO contactDetail(ChatwootContactDetailRequest request) {
        ChatwootContactDetailResponse chatwootContactDetailResponse = chatwootContactService.contactDetail(request);
        if (StringUtils.hasText(chatwootContactDetailResponse.getError())) {
            logger.error(chatwootContactDetailResponse.getError());
            return null;
        }
        return chatwootContactDetailResponse.getPayload();
    }

    @Override
    public ResponseEntity<?> getConversationList(GuestChatwootConversationListRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactConversationService.getConversationList(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> createConversation(GuestChatwootCreateConversationRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactConversationService.createConversation(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactConversationService.getConversationDetail(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactConversationService.updateLastSeen(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getMessages(GuestChatwootGetMessagesRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactMessageService.getMessages(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> sendMessage(GuestChatwootSendMessageRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactMessageService.sendMessage(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = chatwootContactMessageService.updateMessage(request, guestId);
        return ResponseEntity.ok(response);
    }
    
}
