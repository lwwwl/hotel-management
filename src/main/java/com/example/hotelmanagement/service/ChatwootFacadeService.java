package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddNewAgentRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserUpdateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactCreateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactUpdateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootConversationCountRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootConversationListRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootCreateConversationRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUpdateConversationRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootConversationDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddConversationLabelRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootAssignConversationRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUpdateConversationCustomAttributesRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootToggleConversationStatusRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootCreateMessageRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootGetMessagesRequest;

import org.springframework.http.ResponseEntity;

public interface ChatwootFacadeService {

    /* -------------- 用户管理 -------------- */

    ResponseEntity<?> createUser(ChatwootAddNewAgentRequest request);

    ResponseEntity<?> getUserDetail(ChatwootUserDetailRequest request);

    ResponseEntity<?> updateUser(ChatwootUserUpdateRequest request);

    ResponseEntity<?> deleteUser(ChatwootUserDeleteRequest request);

    // -------------- 联系人管理 --------------

    ResponseEntity<?> createContact(ChatwootContactCreateRequest request);

    ResponseEntity<?> updateContact(ChatwootContactUpdateRequest request);

    ResponseEntity<?> deleteContact(ChatwootContactDeleteRequest request);

    ResponseEntity<?> contactDetail(ChatwootContactDetailRequest request);

    // -------------- 会话管理 --------------

    ResponseEntity<?> getConversationCount(ChatwootConversationCountRequest request);

    ResponseEntity<?> getConversationList(ChatwootConversationListRequest request);

    ResponseEntity<?> createConversation(ChatwootCreateConversationRequest request);

    ResponseEntity<?> updateConversation(ChatwootUpdateConversationRequest request);

    ResponseEntity<?> getConversationDetail(ChatwootConversationDetailRequest request);

    ResponseEntity<?> addConversationLabel(ChatwootAddConversationLabelRequest request);

    ResponseEntity<?> assignConversation(ChatwootAssignConversationRequest request);

    ResponseEntity<?> updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request);

    ResponseEntity<?> toggleConversationStatus(ChatwootToggleConversationStatusRequest request);

    // -------------- 消息管理 --------------

    ResponseEntity<?> createMessage(ChatwootCreateMessageRequest request);

    ResponseEntity<?> getMessages(ChatwootGetMessagesRequest request);
}
