package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;

import java.util.Map;

@Data
public class ChatwootUserUpdateRequest {
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String name;
    
    /**
     * 显示名称
     */
    private String displayName;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 密码 - 必须包含大写、小写字母、数字和特殊字符
     */
    private String password;
    
    /**
     * 自定义属性
     */
    private Map<String, Object> customAttributes;
} 