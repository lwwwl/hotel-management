package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootToggleConversationStatusRequest {
    private String accessToken;

    private Long conversationId;
    private String status;
} 