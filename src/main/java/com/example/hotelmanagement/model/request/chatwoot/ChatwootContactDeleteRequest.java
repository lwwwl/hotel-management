package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
 
@Data
public class ChatwootContactDeleteRequest {
    private Long contactId;
    private String accessToken;
} 