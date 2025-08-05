package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootMessagesMetaBO implements Serializable {
    @JsonProperty("labels")
    private List<String> labels;
    @JsonProperty("additional_attributes")
    private Map<String, Object> additionalAttributes;
    @JsonProperty("contact")
    private ChatwootContactDetailBO contact;
    @JsonProperty("assignee")
    private ChatwootAssigneeBO assignee;
    @JsonProperty("agent_last_seen_at")
    private String agentLastSeenAt;
    @JsonProperty("assignee_last_seen_at")
    private String assigneeLastSeenAt;
} 