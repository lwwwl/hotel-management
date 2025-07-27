package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootAddConversationLabelResponse implements Serializable {
    private List<String> payload;
    private String error;
} 