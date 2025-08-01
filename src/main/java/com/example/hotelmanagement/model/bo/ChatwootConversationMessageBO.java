package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootConversationMessageBO implements Serializable {
    @JsonProperty("content")
    private String content;
    @JsonProperty("template_params")
    private Map<String, Object> templateParams;
} 