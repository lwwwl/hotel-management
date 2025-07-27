package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootInboxMemberRequest {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 账户ID
     */
    private Long accountId;
    
    /**
     * 收件箱ID
     */
    private Long inboxId;
} 