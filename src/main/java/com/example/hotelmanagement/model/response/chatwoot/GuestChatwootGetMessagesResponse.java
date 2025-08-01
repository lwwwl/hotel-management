package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import java.util.List;
import com.example.hotelmanagement.model.bo.ChatwootMessageBO;

@Data
public class GuestChatwootGetMessagesResponse {
    private List<ChatwootMessageBO> messages;
    private String error;
} 