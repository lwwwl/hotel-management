package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootConversationListDataBO implements Serializable {
    @JsonProperty("meta")
    private ChatwootConversationMetaBO meta;
    @JsonProperty("payload")
    private List<ChatwootConversationBO> payload;
} 