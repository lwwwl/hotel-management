package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootConversationListDataBO;
import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootConversationListResponse implements Serializable {
    private ChatwootConversationListDataBO data;
    private String error;
} 