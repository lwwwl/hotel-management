package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * 用户锁定/解锁请求对象
 */
@Data
public class UserLockRequest {
    private Long userId;
} 