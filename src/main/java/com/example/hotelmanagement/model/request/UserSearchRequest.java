package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户搜索请求对象
 */
@Data
public class UserSearchRequest {
    private String keyword;
    private Long deptId;
    private Short active;
    
    // 游标分页参数
    private Timestamp lastCreateTime; // 上一页最后一条记录的创建时间
    private Long lastUserId;          // 上一页最后一条记录的用户ID
    private Integer size = 50;        // 每页大小
} 