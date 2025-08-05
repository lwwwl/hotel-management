package com.example.hotelmanagement.model.request.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootAdditionalAttributes;
import lombok.Data;

import java.util.Map;

@Data
public class ChatwootContactCreateRequest {
    private Long inboxId;
    private String name;
    private String email;
    private Boolean blocked;
    private String phoneNumber;
    private String avatarUrl;
    private String identifier;
    private ChatwootAdditionalAttributes additionalAttributes;
    private Map<String, Object> customAttributes;
} 