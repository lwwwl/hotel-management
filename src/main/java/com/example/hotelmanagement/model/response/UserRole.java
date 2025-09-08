package com.example.hotelmanagement.model.response;

import lombok.Data;

@Data
public class UserRole {
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 角色名称
     */
    private String roleName;
}
