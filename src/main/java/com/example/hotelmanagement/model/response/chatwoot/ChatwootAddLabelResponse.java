package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;

import java.util.List;

@Data
public class ChatwootAddLabelResponse {
    /**
     * 标签列表
     */
    private List<String> payload;
    
    /**
     * 错误信息
     */
    private String error;
} 