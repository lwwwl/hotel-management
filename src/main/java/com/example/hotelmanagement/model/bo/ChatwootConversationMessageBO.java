package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
public class ChatwootConversationMessageBO implements Serializable {
    private String content;
    private Map<String, Object> templateParams;
} 