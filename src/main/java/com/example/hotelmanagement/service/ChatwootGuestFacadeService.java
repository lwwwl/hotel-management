package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.chatwoot.*;
import org.springframework.http.ResponseEntity;

public interface ChatwootGuestFacadeService {
    ResponseEntity<?> getConversationList(GuestChatwootConversationListRequest request, Long guestId);
    ResponseEntity<?> createConversation(GuestChatwootCreateConversationRequest request, Long guestId);
    ResponseEntity<?> getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId);
    ResponseEntity<?> updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId);
    ResponseEntity<?> getMessages(GuestChatwootGetMessagesRequest request, Long guestId);
    ResponseEntity<?> sendMessage(GuestChatwootSendMessageRequest request, Long guestId);
    ResponseEntity<?> updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId);
}
