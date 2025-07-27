package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootConversationBO implements Serializable {
    private Long id;
    private List<ChatwootMessageBO> messages;
    private Long accountId;
    private String uuid;
    private Map<String, Object> additionalAttributes;
    private Long agentLastSeenAt;
    private Long assigneeLastSeenAt;
    private Boolean canReply;
    private Long contactLastSeenAt;
    private Map<String, Object> customAttributes;
    private Long inboxId;
    private List<String> labels;
    private Boolean muted;
    private Long snoozedUntil;
    private String status;
    private Long createdAt;
    private Long updatedAt;
    private String timestamp;
    private Long firstReplyCreatedAt;
    private Integer unreadCount;
    private ChatwootMessageBO lastNonActivityMessage;
    private Long lastActivityAt;
    private String priority;
    private Long waitingSince;
    private Long slaPolicyId;
    private Map<String, Object> appliedSla;
    private List<Map<String, Object>> slaEvents;
    private ChatwootConversationMetaBO meta;
} 