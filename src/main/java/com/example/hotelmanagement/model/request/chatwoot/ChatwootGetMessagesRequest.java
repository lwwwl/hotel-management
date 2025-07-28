package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootGetMessagesRequest {
    private Long accountId;
    private Long conversationId;
} 