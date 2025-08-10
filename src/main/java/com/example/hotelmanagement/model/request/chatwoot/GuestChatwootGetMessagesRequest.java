package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class GuestChatwootGetMessagesRequest {
    private String contactIdentifier;
    private Long conversationId;
}