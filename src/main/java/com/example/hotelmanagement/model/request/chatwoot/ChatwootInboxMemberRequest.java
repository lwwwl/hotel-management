package com.example.hotelmanagement.model.request.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatwootInboxMemberRequest {
    /**
     * 用户ID
     */
    @JsonProperty("user_ids")
    private List<Long> userIds;

    @JsonProperty("inbox_id")
    private Long inboxId;
} 