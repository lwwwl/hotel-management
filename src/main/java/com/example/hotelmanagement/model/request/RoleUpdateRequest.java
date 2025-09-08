package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * 角色更新请求对象（仅更新基本信息）
 */
@Data
public class RoleUpdateRequest {
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 角色名称
     */
    private String name;
    
    /**
     * 角色描述
     */
    private String description;
}


