package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

import java.util.List;

@Data
public class ChatwootAddLabelRequest {
    
    /**
     * 联系人ID
     */
    private Long contactId;
    
    /**
     * 标签列表
     */
    private List<String> labels;
} 