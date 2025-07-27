package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootCreateConversationResponse implements Serializable {
    private Long id;
    private Long accountId;
    private Long inboxId;
    private String error;
} 