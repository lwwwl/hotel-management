package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
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
import com.example.hotelmanagement.service.ChatwootUserFacadeService;
import com.example.hotelmanagement.util.UserContext;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/chat-user")
public class ChatwootController {

    @Resource
    private ChatwootUserFacadeService chatwootUserFacadeService;

    /* -------------- 用户管理 -------------- */

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody ChatwootAddNewAgentRequest request) {
        return chatwootUserFacadeService.createUser(request, UserContext.getUserId());
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestBody ChatwootUserDetailRequest request) {
        return chatwootUserFacadeService.getUserDetail(request, UserContext.getUserId());
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody ChatwootUserUpdateRequest request) {
        return chatwootUserFacadeService.updateUser(request, UserContext.getUserId());
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody ChatwootUserDeleteRequest request) {
        return chatwootUserFacadeService.deleteUser(request, UserContext.getUserId());
    }

    /* -------------- 会话管理 -------------- */

    @PostMapping("/conversation/count")
    public ResponseEntity<?> getConversationCount(@RequestBody ChatwootConversationCountRequest request) {
        return chatwootUserFacadeService.getConversationCount(request, UserContext.getUserId());
    }

    @PostMapping("/conversation-list")
    public ResponseEntity<?> getConversationList(@RequestBody ChatwootConversationListRequest request) {
        return chatwootUserFacadeService.getConversationList(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/create")
    public ResponseEntity<?> createConversation(@RequestBody ChatwootCreateConversationRequest request) {
        return chatwootUserFacadeService.createConversation(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/update")
    public ResponseEntity<?> updateConversation(@RequestBody ChatwootUpdateConversationRequest request) {
        return chatwootUserFacadeService.updateConversation(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/detail")
    public ResponseEntity<?> getConversationDetail(@RequestBody ChatwootConversationDetailRequest request) {
        return chatwootUserFacadeService.getConversationDetail(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/add-label")
    public ResponseEntity<?> addConversationLabel(@RequestBody ChatwootAddConversationLabelRequest request) {
        return chatwootUserFacadeService.addConversationLabel(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/assign")
    public ResponseEntity<?> assignConversation(@RequestBody ChatwootAssignConversationRequest request) {
        return chatwootUserFacadeService.assignConversation(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/update-custom-attributes")
    public ResponseEntity<?> updateConversationCustomAttributes(@RequestBody ChatwootUpdateConversationCustomAttributesRequest request) {
        return chatwootUserFacadeService.updateConversationCustomAttributes(request, UserContext.getUserId());
    }

    @PostMapping("/conversation/toggle-status")
    public ResponseEntity<?> toggleConversationStatus(@RequestBody ChatwootToggleConversationStatusRequest request) {
        return chatwootUserFacadeService.toggleConversationStatus(request, UserContext.getUserId());
    }

    /* -------------- 消息管理 -------------- */

    @PostMapping("/message-create")
    public ResponseEntity<?> createMessage(@RequestBody ChatwootCreateMessageRequest request) {
        return chatwootUserFacadeService.createMessage(request, UserContext.getUserId());
    }

    @PostMapping("/message-list")
    public ResponseEntity<?> getMessages(@RequestBody ChatwootGetMessagesRequest request) {
        return chatwootUserFacadeService.getMessages(request, UserContext.getUserId());
    }

}
