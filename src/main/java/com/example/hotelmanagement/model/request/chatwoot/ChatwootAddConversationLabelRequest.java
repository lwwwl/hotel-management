package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
import java.util.List;

@Data
public class ChatwootAddConversationLabelRequest {
    private String accessToken;

    private Long accountId;
    private Long conversationId;
    private List<String> labels;
} 