package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatwootConversationMetaBO implements Serializable {
    @JsonProperty("mine_count")
    private Integer mineCount;
    @JsonProperty("unassigned_count")
    private Integer unassignedCount;
    @JsonProperty("assigned_count")
    private Integer assignedCount;
    @JsonProperty("all_count")
    private Integer allCount;
} 