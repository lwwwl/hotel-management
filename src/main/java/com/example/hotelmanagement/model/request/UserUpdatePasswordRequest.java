package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * App端修改密码请求
 */
@Data
public class UserUpdatePasswordRequest {
    /**
     * 旧密码
     */
    private String oldPassword;
    
    /**
     * 新密码
     */
    private String newPassword;
}

