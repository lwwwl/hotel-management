package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootToggleStatusPayloadBO implements Serializable {
    private Boolean success;
    @JsonProperty("current_status")
    private String currentStatus;
    @JsonProperty("conversation_id")
    private Long conversationId;
} 