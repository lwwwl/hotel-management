package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootInboxMemberRequest {
    private String accessToken;

    /**
     * 用户ID
     */
    private Long userId;
} 