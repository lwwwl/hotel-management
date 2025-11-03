package com.example.hotelmanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * App登录响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppLoginResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 访问令牌
     */
    private String token;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 显示名称
     */
    private String displayName;
}

