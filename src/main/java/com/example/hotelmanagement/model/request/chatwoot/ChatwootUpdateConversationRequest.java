package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootUpdateConversationRequest {
    private Long accountId;
    private Long conversationId;
    private String priority;
    private Long slaPolicyId;
} 