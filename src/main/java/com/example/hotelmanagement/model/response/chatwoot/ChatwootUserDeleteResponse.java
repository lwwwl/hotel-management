package com.example.hotelmanagement.model.response.chatwoot;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatwootUserDeleteResponse implements Serializable {
    // 删除操作通常返回空对象或简单的成功标识
    private String message;
    
    // 错误信息
    private String error;
} 