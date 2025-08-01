package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
import java.util.Map;

@Data
public class GuestChatwootCreateConversationRequest {
    private String inboxIdentifier;
    private String contactIdentifier;
    private Map<String, Object> customAttributes;
} 