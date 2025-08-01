package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import java.util.List;
import com.example.hotelmanagement.model.bo.ChatwootConversationBO;

@Data
public class GuestChatwootConversationListResponse {
    private List<ChatwootConversationBO> conversations;
    private String error;
} 