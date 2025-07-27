package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootAccountUserRequest {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 账户ID
     */
    private Long accountId;
    
    /**
     * 用户角色 - agent 或 administrator
     */
    private String role = "agent";
} 