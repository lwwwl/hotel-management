package com.example.hotelmanagement.model.response;

import lombok.Data;

import java.util.List;

/**
 * 用户详情响应对象
 */
@Data
public class UserDetailResponse {
    private Long userId;
    private String username;
    private String displayName;
    private String employeeNumber;
    private String email;
    private String phone;
    private Boolean superAdmin;
    private Short active;
    private UserDepartmentInfo department;
    private List<UserRoleInfo> roles;
} 