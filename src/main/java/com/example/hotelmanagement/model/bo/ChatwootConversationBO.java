package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootConversationBO implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("messages")
    private List<ChatwootMessageBO> messages;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("additional_attributes")
    private Map<String, Object> additionalAttributes;
    @JsonProperty("agent_last_seen_at")
    private Long agentLastSeenAt;
    @JsonProperty("assignee_last_seen_at")
    private Long assigneeLastSeenAt;
    @JsonProperty("can_reply")
    private Boolean canReply;
    @JsonProperty("contact_last_seen_at")
    private Long contactLastSeenAt;
    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;
    @JsonProperty("inbox_id")
    private Long inboxId;
    @JsonProperty("labels")
    private List<String> labels;
    @JsonProperty("muted")
    private Boolean muted;
    @JsonProperty("snoozed_until")
    private Long snoozedUntil;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("first_reply_created_at")
    private Long firstReplyCreatedAt;
    @JsonProperty("unread_count")
    private Integer unreadCount;
    @JsonProperty("last_non_activity_message")
    private ChatwootMessageBO lastNonActivityMessage;
    @JsonProperty("last_activity_at")
    private Long lastActivityAt;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("waiting_since")
    private Long waitingSince;
    @JsonProperty("sla_policy_id")
    private Long slaPolicyId;
    @JsonProperty("applied_sla")
    private Map<String, Object> appliedSla;
    @JsonProperty("sla_events")
    private List<Map<String, Object>> slaEvents;
    /**
     * 会话元信息，结构见ChatwootConversationMetaDetailBO，包含sender、assignee、channel等
     */
    @JsonProperty("meta")
    private ChatwootConversationMetaDetailBO meta;
} 