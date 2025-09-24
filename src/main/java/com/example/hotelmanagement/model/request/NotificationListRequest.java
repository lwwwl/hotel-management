package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * 通知列表请求
 */
@Data
public class NotificationListRequest {

    /**
     * 游标分页：上一页最后一条通知的ID
     */
    private Long lastNotificationId;

    /**
     * 每页大小，默认100
     */
    private Integer size = 100;
}
