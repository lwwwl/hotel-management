package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootUpdateConversationRequest {
    private String accessToken;

    private Long conversationId;
    private String priority;
    private Long slaPolicyId;
} 