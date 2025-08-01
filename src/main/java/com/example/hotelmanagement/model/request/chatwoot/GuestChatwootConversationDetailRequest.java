package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class GuestChatwootConversationDetailRequest {
    private String inboxIdentifier;
    private String contactIdentifier;
    private Long conversationId;
} 