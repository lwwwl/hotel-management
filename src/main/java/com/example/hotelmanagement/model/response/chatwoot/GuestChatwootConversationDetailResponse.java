package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import java.util.List;
import java.util.Map;
import com.example.hotelmanagement.model.bo.ChatwootMessageBO;

@Data
public class GuestChatwootConversationDetailResponse {
    private Long id;
    private String inboxId;
    private List<ChatwootMessageBO> messages;
    private Map<String, Object> contact;
    private String error;
} 