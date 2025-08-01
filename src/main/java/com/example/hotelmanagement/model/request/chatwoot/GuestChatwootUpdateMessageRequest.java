package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
import java.util.Map;

@Data
public class GuestChatwootUpdateMessageRequest {
    private String inboxIdentifier;
    private String contactIdentifier;
    private Long conversationId;
    private String messageId;
    private Map<String, Object> submittedValues;
} 