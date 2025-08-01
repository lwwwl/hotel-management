package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import com.example.hotelmanagement.service.ChatwootGuestFacadeService;
import com.example.hotelmanagement.service.chatwoot.GuestChatwootConversationService;
import com.example.hotelmanagement.service.chatwoot.GuestChatwootMessageService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatwootGuestFacadeServiceImpl implements ChatwootGuestFacadeService {

    @Resource
    private GuestChatwootConversationService guestChatwootConversationService;

    @Resource
    private GuestChatwootMessageService guestChatwootMessageService;

    @Override
    public ResponseEntity<?> getConversationList(GuestChatwootConversationListRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootConversationService.getConversationList(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> createConversation(GuestChatwootCreateConversationRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootConversationService.createConversation(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootConversationService.getConversationDetail(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootConversationService.updateLastSeen(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getMessages(GuestChatwootGetMessagesRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootMessageService.getMessages(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> sendMessage(GuestChatwootSendMessageRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootMessageService.sendMessage(request, guestId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId) {
        if (guestId == null) {
            return ResponseEntity.badRequest().body("访客ID不能为空");
        }
        var response = guestChatwootMessageService.updateMessage(request, guestId);
        return ResponseEntity.ok(response);
    }
    
}
