package com.example.hotelmanagement.model.request;

import java.util.List;

import lombok.Data;

/**
 * 角色更新用户请求对象
 */
@Data
public class RoleUpdateUserRequest {
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 关联的用户ID列表
     */
    private List<Long> userIdList;
}
