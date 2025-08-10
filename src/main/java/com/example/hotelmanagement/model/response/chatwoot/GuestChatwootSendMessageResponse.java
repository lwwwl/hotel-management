package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import com.example.hotelmanagement.model.bo.ChatwootMessageBO;

@Data
public class GuestChatwootSendMessageResponse extends ChatwootMessageBO {
    private ChatwootMessageBO message;
    private String error;
} 