package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootConversationBO;
import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootConversationDetailResponse implements Serializable {
    private ChatwootConversationBO data;
    private String error;
} 