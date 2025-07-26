package com.example.hotelmanagement.model.response;

import lombok.Data;

/**
 * 用户列表项响应对象
 */
@Data
public class UserItem {
    private Long userId;
    private String username;
    private String displayName;
    private String employeeNumber;
    private Short active;
    private UserDepartmentInfo department;
} 