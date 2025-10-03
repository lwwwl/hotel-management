package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.repository.HotelGuestRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.example.hotelmanagement.model.bo.ChatwootContactInboxBO;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.CreateContactResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToAccountResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToInboxResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactCreateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootCreateUserResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserDeleteResponse;
import com.example.hotelmanagement.service.ChatwootUserFacadeService;
import com.example.hotelmanagement.service.chatwoot.*;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ChatwootUserFacadeServiceImpl implements ChatwootUserFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(ChatwootUserFacadeServiceImpl.class);

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

    @Resource
    private HotelUserRepository userRepository;

    @Resource
    private HotelGuestRepository guestRepository;


    @Value("${chatwoot.account.id}")
    private Long ACCOUNT_ID;

    @Value("${chatwoot.inbox.id}")
    private Long INBOX_ID;

    @Override
    public ResponseEntity<?> createUser(ChatwootAddNewAgentRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        logger.info("开始创建用户并关联到账户和收件箱: {}", request.getName());

        try {
            // 步骤1: 创建用户
            logger.info("步骤1: 创建用户并和Account创建关联");
            ChatwootCreateUserResponse createUserResponse = chatwootUserService.createUser(request);

            if (createUserResponse == null ||
                    StringUtils.hasText(createUserResponse.getError())) {
                String errMsg = createUserResponse == null ? "" :  createUserResponse.getError();
                logger.error("创建用户失败: {}",  errMsg);
                return ResponseEntity.badRequest().body(errMsg);
            }
            // 从响应中提取用户ID
            long cwUserId = createUserResponse.getId();
            logger.info("用户创建成功，用户ID: {}", cwUserId);

            // 步骤2: 关联用户到账户
            logger.info("步骤2: 关联用户到账户");
            ChatwootAccountUserRequest accountUserRequest = new ChatwootAccountUserRequest();
            accountUserRequest.setUserId(cwUserId);
            ChatwootAddUserToAccountResponse addUserToAccountResponse = chatwootAccountService.addUserToAccount(accountUserRequest);
            if (addUserToAccountResponse == null ||
                    StringUtils.hasText(addUserToAccountResponse.getError())) {
                String errMsg = addUserToAccountResponse == null ? "" :  addUserToAccountResponse.getError();
                logger.error("关联用户到账户失败: {}",  errMsg);
                return ResponseEntity.badRequest().body(errMsg);
            }
            logger.info("用户关联到账户成功");

            // 步骤3: 关联用户到收件箱
            logger.info("步骤3: 关联用户到收件箱");
            ChatwootInboxMemberRequest inboxMemberRequest = new ChatwootInboxMemberRequest();
            inboxMemberRequest.setInboxId(INBOX_ID);
            inboxMemberRequest.setUserIds(List.of(cwUserId));

            ChatwootAddUserToInboxResponse addUserToInboxResponse = chatwootInboxService.addUserToInbox(inboxMemberRequest);

            if (addUserToInboxResponse == null ||
                    StringUtils.hasText(addUserToInboxResponse.getError())) {
                String errMsg = addUserToInboxResponse == null ? "" :  addUserToInboxResponse.getError();
                logger.error("关联用户到收件箱失败: {}",  errMsg);
                return ResponseEntity.badRequest().body(errMsg);
            }
            logger.info("用户关联到收件箱成功");

            // 用户的cw_user_id, cw_api_access_token落库user表
            userRepository.updateCwUserId(userId, createUserResponse.getId(), createUserResponse.getAccessToken());

            // 返回成功响应
            return ResponseEntity.ok(ApiResponse.success(cwUserId));

        } catch (Exception e) {
            logger.error("创建用户并关联过程中发生异常: {}", e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "创建用户失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getUserDetail(ChatwootUserDetailRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootUserService.getUserDetail(request));
    }

    @Override
    public ResponseEntity<?> updateUser(ChatwootUserUpdateRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootUserService.updateUser(request));
    }

    @Override
    public ResponseEntity<?> deleteUser(ChatwootUserDeleteRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        request.setAccessToken(currentUser.getCwApiAccessToken());

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
    public ResponseEntity<?> getConversationCount(ChatwootConversationCountRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.getConversationCount(request));
    }

    @Override
    public ResponseEntity<?> getConversationList(ChatwootConversationListRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.getConversationList(request));
    }

    @Override
    public ResponseEntity<?> createConversation(ChatwootCreateConversationRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.createConversation(request));
    }

    @Override
    public ResponseEntity<?> updateConversation(ChatwootUpdateConversationRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.updateConversation(request));
    }

    @Override
    public ResponseEntity<?> getConversationDetail(ChatwootConversationDetailRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.getConversationDetail(request));
    }

    @Override
    public ResponseEntity<?> addConversationLabel(ChatwootAddConversationLabelRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.addConversationLabel(request));
    }

    @Override
    public ResponseEntity<?> assignConversation(ChatwootAssignConversationRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }

        if (!StringUtils.hasText(currentUser.getCwApiAccessToken()) || currentUser.getCwUserId() == null) {
            return ResponseEntity.ok(ApiResponse.error("此账户Chatwoot帐号信息不完整，操作失败，请联系管理员。UserId:" + currentUser.getId()));
        }
        // todo 上报事件

        return ResponseEntity.ok(chatwootConversationService.assignConversation(request, currentUser.getCwApiAccessToken(), currentUser.getCwUserId()));
    }

    @Override
    public ResponseEntity<?> updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootConversationService.updateConversationCustomAttributes(request));
    }

    @Override
    public ResponseEntity<?> toggleConversationStatus(ChatwootToggleConversationStatusRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        if (!StringUtils.hasText(currentUser.getCwApiAccessToken()) || currentUser.getCwUserId() == null) {
            return ResponseEntity.ok(ApiResponse.error("此账户Chatwoot帐号信息不完整，操作失败，请联系管理员。UserId:" + currentUser.getId()));
        }
        // todo 上报事件

        return ResponseEntity.ok(chatwootConversationService.toggleConversationStatus(request, currentUser.getCwApiAccessToken()));
    }

    @Override
    public ResponseEntity<?> createMessage(ChatwootCreateMessageRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootMessageService.createMessage(request));
    }

    @Override
    public ResponseEntity<?> getMessages(ChatwootGetMessagesRequest request, Long userId) {
        HotelUser currentUser = getCurrentUser(userId);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        request.setAccessToken(currentUser.getCwApiAccessToken());

        return ResponseEntity.ok(chatwootMessageService.getMessages(request));
    }

    // 判空获取sourceId
    private String safeGetSourceId(ChatwootContactCreateResponse resp) {
        if (resp == null || resp.getPayload() == null || resp.getPayload().isEmpty()) {
            return null;
        }
        ChatwootContactDetailBO detail = resp.getPayload().get(0);
        if (detail == null || detail.getContactInboxes() == null || detail.getContactInboxes().isEmpty()) {
            return null;
        }
        ChatwootContactInboxBO inbox = detail.getContactInboxes().get(0);
        return inbox != null ? inbox.getSourceId() : null;
    }

    private HotelUser getCurrentUser(Long userId) {
        HotelUser hotelUser = userRepository.findById(userId).orElse(null);
        return hotelUser;
    }
}
