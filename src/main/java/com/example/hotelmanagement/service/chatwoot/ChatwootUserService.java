package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddNewAgentRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserUpdateRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootCreateUserResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserDetailResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserUpdateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootUserDeleteResponse;

public interface ChatwootUserService {
    /**
     * 创建Chatwoot用户
     */
    ChatwootCreateUserResponse createUser(ChatwootAddNewAgentRequest request);

    /**
     * 获取Chatwoot用户详情
     */
    ChatwootUserDetailResponse getUserDetail(ChatwootUserDetailRequest request);

    /**
     * 更新Chatwoot用户
     */
    ChatwootUserUpdateResponse updateUser(ChatwootUserUpdateRequest request);

    /**
     * 删除Chatwoot用户
     */
    ChatwootUserDeleteResponse deleteUser(ChatwootUserDeleteRequest request);
}
