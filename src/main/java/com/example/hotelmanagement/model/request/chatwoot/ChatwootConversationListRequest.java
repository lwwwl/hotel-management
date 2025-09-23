package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootConversationListRequest {
    private String accessToken;

    private String label;
    private String assigneeType;
    private String status;
} 