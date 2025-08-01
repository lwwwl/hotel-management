package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootConversationCountRequest {
    private String accessToken;
    private Long accountId;
} 