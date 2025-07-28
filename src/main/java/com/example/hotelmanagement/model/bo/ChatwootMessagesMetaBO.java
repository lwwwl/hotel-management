package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootMessagesMetaBO implements Serializable {
    private List<String> labels;
    private Map<String, Object> additionalAttributes;
    private ChatwootContactBO contact;
    private ChatwootAssigneeBO assignee;
    private String agentLastSeenAt;
    private String assigneeLastSeenAt;
} 