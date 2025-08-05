package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootUserDeleteRequest {

    private String accessToken;
    /**
     * 用户ID
     */
    private Long id;
} 