package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootAgentBO;
import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootAssignConversationResponse implements Serializable {
    private ChatwootAgentBO agent;
    private String error;
} 