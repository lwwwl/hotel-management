package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dao.entity.HotelGuest;
import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.example.hotelmanagement.model.request.chatwoot.*;
import org.springframework.http.ResponseEntity;

public interface ChatwootGuestFacadeService {

    /* -------------- 客人相关 -------------- */

    ChatwootContactDetailBO createContact(ChatwootContactCreateRequest request);

    ChatwootContactDetailBO updateContact(ChatwootContactUpdateRequest request);

    Boolean deleteContact(ChatwootContactDeleteRequest request, Long guestId);

    ChatwootContactDetailBO contactDetail(ChatwootContactDetailRequest request);


    /* -------------- 会话消息相关 -------------- */

    ResponseEntity<?> getConversationList(GuestChatwootConversationListRequest request, Long guestId);

    ResponseEntity<?> createConversation(GuestChatwootCreateConversationRequest request, Long guestId);

    ResponseEntity<?> getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId);

    ResponseEntity<?> updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId);

    ResponseEntity<?> getMessages(GuestChatwootGetMessagesRequest request, Long guestId);

    ResponseEntity<?> sendMessage(GuestChatwootSendMessageRequest request, Long guestId);

    ResponseEntity<?> updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId);
}
