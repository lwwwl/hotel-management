package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import com.example.hotelmanagement.model.bo.ChatwootMessageBO;

@Data
public class GuestChatwootUpdateMessageResponse extends ChatwootMessageBO {
    private String error;
} 