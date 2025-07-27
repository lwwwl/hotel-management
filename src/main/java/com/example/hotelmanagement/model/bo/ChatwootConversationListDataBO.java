package com.example.hotelmanagement.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootConversationListDataBO implements Serializable {
    private ChatwootConversationMetaBO meta;
    private List<ChatwootConversationBO> payload;
} 