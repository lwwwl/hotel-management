package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootToggleStatusPayloadBO;
import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
public class ChatwootToggleConversationStatusResponse implements Serializable {
    private Map<String, Object> meta;
    private ChatwootToggleStatusPayloadBO payload;
    private String error;
} 