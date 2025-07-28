package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootMessageBO;
import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootCreateMessageResponse implements Serializable {
    private ChatwootMessageBO message;
    private String error;
} 