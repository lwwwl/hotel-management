package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class GuestChatwootSendMessageRequest {
    private String contactIdentifier;
    private Long conversationId;
    private String content;
    private String echoId;
} 