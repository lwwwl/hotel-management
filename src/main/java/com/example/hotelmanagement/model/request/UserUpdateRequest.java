package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.util.List;

/**
 * 用户更新请求对象
 */
@Data
public class UserUpdateRequest {
    private Long userId;
    private String username;
    private String password;
    private String displayName;
    private String employeeNumber;
    private String email;
    private String phone;
    private List<Long> roleIds;
    private Long deptId;
    private Boolean superAdmin;
} 