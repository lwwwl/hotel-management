package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootToggleConversationStatusRequest {
    private Long accountId;
    private Long conversationId;
    private String status;
} 