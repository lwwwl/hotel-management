package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
import java.util.Map;

@Data
public class ChatwootUpdateConversationCustomAttributesRequest {
    private Long accountId;
    private Long conversationId;
    private Map<String, Object> customAttributes;
} 