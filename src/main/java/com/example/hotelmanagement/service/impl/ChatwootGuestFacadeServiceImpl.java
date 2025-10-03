package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.HotelGuest;
import com.example.hotelmanagement.dao.repository.HotelGuestRepository;
import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.example.hotelmanagement.model.bo.ChatwootCreateContactRespBO;
import com.example.hotelmanagement.model.bo.ChatwootGuestCreateConversationRespBO;
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

import java.util.List;
import java.util.Map;

@Service
public class ChatwootGuestFacadeServiceImpl implements ChatwootGuestFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootGuestFacadeServiceImpl.class);

    @Resource
    private ChatwootContactService chatwootContactService;

    @Resource
    private ChatwootContactConversationService chatwootContactConversationService;

    @Resource
    private ChatwootContactMessageService chatwootContactMessageService;

    @Resource
    private HotelGuestRepository hotelGuestRepository;

    @Override
    public ChatwootCreateContactRespBO createContact(ChatwootContactCreateRequest request) {
        return chatwootContactService.createContact(request);
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
    public ChatwootGuestCreateConversationRespBO createConversation(GuestChatwootCreateConversationRequest request) {
        return chatwootContactConversationService.createConversation(request);
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
    public ResponseEntity<?> getMessages(Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        HotelGuest hotelGuest = hotelGuestRepository.findById(guestId).orElse(null);
        if (hotelGuest == null) {
            return ResponseEntity.badRequest().body("访客ID不存在");
        }
        GuestChatwootGetMessagesRequest request = new GuestChatwootGetMessagesRequest();
        request.setContactIdentifier(hotelGuest.getChatwootSourceId());
        request.setConversationId(hotelGuest.getChatwootConversationId());

        Map<String, Object> response = chatwootContactMessageService.getMessages(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> sendMessage(String content, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        HotelGuest hotelGuest = hotelGuestRepository.findById(guestId).orElse(null);
        if (hotelGuest == null) {
            return ResponseEntity.badRequest().body("访客ID不存在");
        }
        GuestChatwootSendMessageRequest request = new  GuestChatwootSendMessageRequest();
        request.setContactIdentifier(hotelGuest.getChatwootSourceId());
        request.setConversationId(hotelGuest.getChatwootConversationId());
        request.setContent(content);

        Map<String, Object> response = chatwootContactMessageService.sendMessage(request);
        return ResponseEntity.ok(response);
    }

//    @Override
//    public ResponseEntity<?> updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId) {
//        if (guestId == null) {
//            return ResponseEntity.badRequest().body("访客ID不能为空");
//        }
//        var response = chatwootContactMessageService.updateMessage(request);
//        return ResponseEntity.ok(response);
//    }
    
}
