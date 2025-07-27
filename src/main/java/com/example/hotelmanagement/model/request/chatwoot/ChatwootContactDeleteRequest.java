package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
 
@Data
public class ChatwootContactDeleteRequest {
    private Long accountId;
    private Long contactId;
} 