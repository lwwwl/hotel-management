package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
public class ChatwootMessageBO implements Serializable {
    private Long id;
    private String content;
    private Long accountId;
    private Long inboxId;
    private Long conversationId;
    private Integer messageType;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isPrivate;
    private String status;
    private String sourceId;
    private String contentType;
    private Map<String, Object> contentAttributes;
    private String senderType;
    private Long senderId;
    private Map<String, Object> externalSourceIds;
    private Map<String, Object> additionalAttributes;
    private String processedMessageContent;
    private Map<String, Object> sentiment;
    private Map<String, Object> conversation;
    private Map<String, Object> attachment;
    private Map<String, Object> sender;
} 