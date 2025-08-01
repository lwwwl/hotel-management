package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootGetMessagesRequest {
    private String accessToken;

    private Long conversationId;
} 