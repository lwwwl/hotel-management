package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class GuestChatwootConversationListRequest {
    private String inboxIdentifier;
    private String contactIdentifier;
} 