package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootMessageBO implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("inbox_id")
    private Long inboxId;
    @JsonProperty("conversation_id")
    private Long conversationId;
    @JsonProperty("message_type")
    private Integer messageType;
    @JsonProperty("created_at")
    private Long createdAt;
//    @JsonProperty("updated_at")
//    private Long updatedAt;
    @JsonProperty("private")
    private Boolean isPrivate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("source_id")
    private String sourceId;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("content_attributes")
    private Map<String, Object> contentAttributes;
    @JsonProperty("sender_type")
    private String senderType;
    @JsonProperty("sender_id")
    private Long senderId;
    @JsonProperty("external_source_ids")
    private Map<String, Object> externalSourceIds;
    @JsonProperty("additional_attributes")
    private ChatwootAdditionalAttributes additionalAttributes;
    @JsonProperty("processed_message_content")
    private String processedMessageContent;
    @JsonProperty("sentiment")
    private Map<String, Object> sentiment;
    @JsonProperty("conversation")
    private Map<String, Object> conversation;
    @JsonProperty("attachment")
    private Map<String, Object> attachment;
    @JsonProperty("sender")
    private ChatwootSenderBO sender;
} 