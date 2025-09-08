package com.example.hotelmanagement.model.request;

import java.util.List;

import lombok.Data;

/**
 * 角色更新菜单请求对象
 */
@Data
public class RoleUpdateMenuRequest {
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 关联的菜单ID列表
     */
    private List<Long> menuIdList;
}
