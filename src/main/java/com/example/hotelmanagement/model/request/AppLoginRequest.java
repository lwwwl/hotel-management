package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * App登录请求
 */
@Data
public class AppLoginRequest {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 设备信息（可选）
     */
    private String deviceInfo;
}

