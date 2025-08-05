package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootGetMessagesRequest {
    private String accessToken;

    private Integer before;
    private Integer after;
    private Long conversationId;
} 