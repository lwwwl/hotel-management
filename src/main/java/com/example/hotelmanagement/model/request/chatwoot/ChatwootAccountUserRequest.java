package com.example.hotelmanagement.model.request.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatwootAccountUserRequest {
    /**
     * 用户ID
     */
    @JsonProperty("user_id")
    private Long userId;
    
    /**
     * 用户角色 - agent 或 administrator
     */
    private String role = "agent";
} 