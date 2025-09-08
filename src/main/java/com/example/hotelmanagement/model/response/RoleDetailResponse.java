package com.example.hotelmanagement.model.response;

import java.util.List;

import lombok.Data;

/**
 * 角色详情响应对象
 */
@Data
public class RoleDetailResponse {
    /**
     * 角色基本信息
     */
    private RoleBasicInfo roleInfo;
    
    /**
     * 关联的菜单列表
     */
    private List<MenuDetailInfo> menus;
    
    /**
     * 关联的用户列表（包含部门信息）
     */
    private List<UserDetailInfo> users;
    
    /**
     * 角色基本信息
     */
    @Data
    public static class RoleBasicInfo {
        private Long roleId;
        private String name;
        private String description;
        private Integer memberCount;
        private Long createTime;
        private Long updateTime;
    }
    
    /**
     * 菜单详情信息
     */
    @Data
    public static class MenuDetailInfo {
        private Long menuId;
        private String menuName;
        private String icon;
        private Integer sortOrder;
    }
    
    /**
     * 用户详情信息（包含部门信息）
     */
    @Data
    public static class UserDetailInfo {
        private Long userId;
        private String username;
        private String displayName;
        private String employeeNumber;
        private Short active;
        private List<DepartmentInfo> departments;
        
        /**
         * 部门信息
         */
        @Data
        public static class DepartmentInfo {
            private Long deptId;
            private String deptName;
        }
    }
}
