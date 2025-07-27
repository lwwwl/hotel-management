package com.example.hotelmanagement.model.request.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootConversationMessageBO;
import lombok.Data;
import java.util.Map;

@Data
public class ChatwootCreateConversationRequest {
    private String sourceId;
    private Long inboxId;
    private Long contactId;
    private Map<String, Object> additionalAttributes;
    private Map<String, Object> customAttributes;
    private String status;
    private Long assigneeId;
    private Long teamId;
    private String snoozedUntil;
    private ChatwootConversationMessageBO message;
} 