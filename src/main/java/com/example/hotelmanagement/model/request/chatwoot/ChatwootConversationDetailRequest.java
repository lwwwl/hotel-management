package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootConversationDetailRequest {
    private Long accountId;
    private Long conversationId;
} 