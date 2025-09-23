package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootConversationCountRequest {
    private String accessToken;
    private String status;
    // 当前写死2
    private Integer inboxId;
} 