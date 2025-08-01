package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
import java.util.Map;

@Data
public class ChatwootCreateMessageRequest {
    private String accessToken;

    private Long conversationId;
    private String content;
    private String messageType;
    private Boolean isPrivate;
    private String contentType;
    private Map<String, Object> contentAttributes;
    private Long campaignId;
    private Map<String, Object> templateParams;
} 