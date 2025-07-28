package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToAccountResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToInboxResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootCreateUserResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserDeleteResponse;
import com.example.hotelmanagement.service.ChatwootFacadeService;
import com.example.hotelmanagement.service.chatwoot.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ChatwootFacadeServiceImpl implements ChatwootFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootFacadeServiceImpl.class);

    @Resource
    private ChatwootAccountService chatwootAccountService;

    @Resource
    private ChatwootInboxService chatwootInboxService;

    @Resource
    private ChatwootUserService chatwootUserService;

    @Resource
    private ChatwootContactService chatwootContactService;

    @Resource
    private ChatwootConversationService chatwootConversationService;

    @Resource
    private ChatwootMessageService chatwootMessageService;


    @Value("${chatwoot.account.id}")
    private Long ACCOUNT_ID;

    @Value("${chatwoot.inbox.id}")
    private Long INBOX_ID;

    @Override
    public ResponseEntity<?> createUser(ChatwootAddNewAgentRequest request) {
        logger.info("开始创建用户并关联到账户和收件箱: {}", request.getName());

        try {
            // 步骤1: 创建用户
            logger.info("步骤1: 创建用户并和Account创建关联");
            ChatwootCreateUserResponse createUserResponse = chatwootUserService.createUser(request);

            if (createUserResponse == null ||
                    !StringUtils.hasText(createUserResponse.getError())) {
                String errMsg = createUserResponse == null ? "" :  createUserResponse.getError();
                logger.error("创建用户失败: {}",  errMsg);
                return ResponseEntity.badRequest().body(errMsg);
            }
            // 从响应中提取用户ID
            long userId = createUserResponse.getId();
            logger.info("用户创建成功，用户ID: {}", userId);

            // 步骤2: 关联用户到收件箱
            logger.info("步骤2: 关联用户到收件箱");
            ChatwootInboxMemberRequest inboxMemberRequest = new ChatwootInboxMemberRequest();
            inboxMemberRequest.setUserId(userId);
            inboxMemberRequest.setAccountId(ACCOUNT_ID);
            inboxMemberRequest.setInboxId(INBOX_ID);

            ChatwootAddUserToInboxResponse addUserToInboxResponse = chatwootInboxService.addUserToInbox(inboxMemberRequest);

            if (addUserToInboxResponse == null ||
                    StringUtils.hasText(addUserToInboxResponse.getError())) {
                String errMsg = addUserToInboxResponse == null ? "" :  addUserToInboxResponse.getError();
                logger.error("关联用户到收件箱失败: {}",  errMsg);
                return ResponseEntity.badRequest().body(errMsg);
            }
            logger.info("用户关联到收件箱成功");

            // todo 落库user表

            // 返回成功响应
            return ResponseEntity.ok(ApiResponse.success(userId));

        } catch (Exception e) {
            logger.error("创建用户并关联过程中发生异常: {}", e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "创建用户失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getUserDetail(ChatwootUserDetailRequest request) {
        return ResponseEntity.ok(chatwootUserService.getUserDetail(request));
    }

    @Override
    public ResponseEntity<?> updateUser(ChatwootUserUpdateRequest request) {
        return ResponseEntity.ok(chatwootUserService.updateUser(request));
    }

    @Override
    public ResponseEntity<?> deleteUser(ChatwootUserDeleteRequest request) {
        ChatwootUserDeleteResponse userDeleteResponse = chatwootUserService.deleteUser(request);
        if (userDeleteResponse == null ||
                    StringUtils.hasText(userDeleteResponse.getError())) {
            String errMsg = userDeleteResponse == null ? "" :  userDeleteResponse.getError();
            logger.error("删除用户失败：: {}", errMsg);
            return ResponseEntity.badRequest().body(errMsg);
        }
        return ResponseEntity.ok(ApiResponse.success(request.getId()));
    }

    @Override
    public ResponseEntity<?> createContact(ChatwootContactCreateRequest request) {
        // todo 存source_id绑定到user表
        return ResponseEntity.ok(chatwootContactService.createContact(request));
    }

    @Override
    public ResponseEntity<?> updateContact(ChatwootContactUpdateRequest request) {
        return ResponseEntity.ok(chatwootContactService.updateContact(request));
    }

    @Override
    public ResponseEntity<?> deleteContact(ChatwootContactDeleteRequest request) {
        return ResponseEntity.ok(chatwootContactService.deleteContact(request));
    }

    @Override
    public ResponseEntity<?> contactDetail(ChatwootContactDetailRequest request) {
        return ResponseEntity.ok(chatwootContactService.contactDetail(request));
    }

    @Override
    public ResponseEntity<?> getConversationCount(ChatwootConversationCountRequest request) {
        return ResponseEntity.ok(chatwootConversationService.getConversationCount(request));
    }

    @Override
    public ResponseEntity<?> getConversationList(ChatwootConversationListRequest request) {
        return ResponseEntity.ok(chatwootConversationService.getConversationList(request));
    }

    @Override
    public ResponseEntity<?> createConversation(ChatwootCreateConversationRequest request) {
        return ResponseEntity.ok(chatwootConversationService.createConversation(request));
    }

    @Override
    public ResponseEntity<?> updateConversation(ChatwootUpdateConversationRequest request) {
        return ResponseEntity.ok(chatwootConversationService.updateConversation(request));
    }

    @Override
    public ResponseEntity<?> getConversationDetail(ChatwootConversationDetailRequest request) {
        return ResponseEntity.ok(chatwootConversationService.getConversationDetail(request));
    }

    @Override
    public ResponseEntity<?> addConversationLabel(ChatwootAddConversationLabelRequest request) {
        return ResponseEntity.ok(chatwootConversationService.addConversationLabel(request));
    }

    @Override
    public ResponseEntity<?> assignConversation(ChatwootAssignConversationRequest request) {
        return ResponseEntity.ok(chatwootConversationService.assignConversation(request));
    }

    @Override
    public ResponseEntity<?> updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request) {
        return ResponseEntity.ok(chatwootConversationService.updateConversationCustomAttributes(request));
    }

    @Override
    public ResponseEntity<?> toggleConversationStatus(ChatwootToggleConversationStatusRequest request) {
        return ResponseEntity.ok(chatwootConversationService.toggleConversationStatus(request));
    }

    @Override
    public ResponseEntity<?> createMessage(ChatwootCreateMessageRequest request) {
        return ResponseEntity.ok(chatwootMessageService.createMessage(request));
    }

    @Override
    public ResponseEntity<?> getMessages(ChatwootGetMessagesRequest request) {
        return ResponseEntity.ok(chatwootMessageService.getMessages(request));
    }
}
