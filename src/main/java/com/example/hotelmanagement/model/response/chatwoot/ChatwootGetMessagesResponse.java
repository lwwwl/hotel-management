package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootMessagesMetaBO;
import com.example.hotelmanagement.model.bo.ChatwootMessageBO;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootGetMessagesResponse implements Serializable {
    private ChatwootMessagesMetaBO meta;
    private List<ChatwootMessageBO> payload;
    private String error;
} 