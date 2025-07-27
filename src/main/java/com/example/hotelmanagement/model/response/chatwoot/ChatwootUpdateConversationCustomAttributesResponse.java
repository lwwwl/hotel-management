package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
public class ChatwootUpdateConversationCustomAttributesResponse implements Serializable {
    private Map<String, Object> customAttributes;
    private String error;
} 