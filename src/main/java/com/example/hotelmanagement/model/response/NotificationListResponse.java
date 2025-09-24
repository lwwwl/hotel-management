package com.example.hotelmanagement.model.response;

import java.util.List;

import lombok.Data;

/**
 * 通知列表响应
 */
@Data
public class NotificationListResponse {

    /**
     * 通知列表
     */
    private List<NotificationItem> notifications;

    /**
     * 是否还有更多记录
     */
    private Boolean hasMore;

    /**
     * 游标：最后一条通知ID
     */
    private Long lastNotificationId;

    /**
     * 每页大小
     */
    private Integer size;

    @Data
    public static class NotificationItem {
        private Long id;
        private String title;
        private String body;
        private Long taskId;
        private String notificationType;
        private Short alreadyRead;
        private Long createTime;
    }
}
