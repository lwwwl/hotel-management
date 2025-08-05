package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddNewAgentRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserUpdateRequest;
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

public interface ChatwootUserFacadeService {

    /* -------------- 用户管理 -------------- */

    ResponseEntity<?> createUser(ChatwootAddNewAgentRequest request, Long userId);

    ResponseEntity<?> getUserDetail(ChatwootUserDetailRequest request, Long userId);

    ResponseEntity<?> updateUser(ChatwootUserUpdateRequest request, Long userId);

    ResponseEntity<?> deleteUser(ChatwootUserDeleteRequest request, Long userId);

    // -------------- 会话管理 --------------

    // todo 获取会话未读数 /api/v1/inbox/{inbox_id}/conversations

    // todo 更新会话未读消息进度 POST /api/v1/accounts/{account_id}/conversations/{conversation_id}/update_last_seen

    ResponseEntity<?> getConversationCount(ChatwootConversationCountRequest request, Long userId);

    ResponseEntity<?> getConversationList(ChatwootConversationListRequest request, Long userId);

    ResponseEntity<?> createConversation(ChatwootCreateConversationRequest request, Long userId);

    ResponseEntity<?> updateConversation(ChatwootUpdateConversationRequest request, Long userId);

    ResponseEntity<?> getConversationDetail(ChatwootConversationDetailRequest request, Long userId);

    ResponseEntity<?> addConversationLabel(ChatwootAddConversationLabelRequest request, Long userId);

    ResponseEntity<?> assignConversation(ChatwootAssignConversationRequest request, Long userId);

    ResponseEntity<?> updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request, Long userId);

    ResponseEntity<?> toggleConversationStatus(ChatwootToggleConversationStatusRequest request, Long userId);

    // -------------- 消息管理 --------------

    ResponseEntity<?> createMessage(ChatwootCreateMessageRequest request, Long userId);

    ResponseEntity<?> getMessages(ChatwootGetMessagesRequest request, Long userId);
}
