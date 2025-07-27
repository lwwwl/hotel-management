package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootAssignConversationRequest {
    private Long accountId;
    private Long conversationId;
    private Long assigneeId;
    private Long teamId;
} 