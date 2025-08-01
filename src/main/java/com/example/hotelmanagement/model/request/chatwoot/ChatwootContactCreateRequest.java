package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

import java.util.Map;

@Data
public class ChatwootContactCreateRequest {
    private Long guestId;
    
    private Long inboxId;
    private String name;
    private String email;
    private Boolean blocked;
    private String phoneNumber;
    private String avatarUrl;
    private String identifier;
    private Map<String, Object> additionalAttributes;
    private Map<String, Object> customAttributes;
} 