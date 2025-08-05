package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

@Data
public class ChatwootContactDetailRequest {
    private Long accountId;
    private Long contactId;
    private String accessToken;
}