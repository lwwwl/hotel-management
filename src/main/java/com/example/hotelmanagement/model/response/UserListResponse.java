package com.example.hotelmanagement.model.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户列表响应对象
 */
@Data
public class UserListResponse {
    private List<UserItem> users;
    private Boolean hasMore;               // 是否有更多记录
    private Long lastCreateTime;      // 最后一条记录的创建时间（作为下次查询的游标）
    private Long lastUserId;               // 最后一条记录的用户ID（作为下次查询的游标）
    private Integer size;                  // 每页大小
} 