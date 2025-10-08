package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootToggleConversationStatusRequest {
    private Long conversationId;
    // open, resolved, pending, snoozed
    private String status = "resolved";
} 