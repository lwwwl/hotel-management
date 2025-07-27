package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootConversationMetaBO;
import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootConversationCountResponse implements Serializable {
    private ChatwootConversationMetaBO meta;
    private String error;
} 