package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.NotificationListRequest;

public interface HotelNotificationService {

    /**
     * 查询用户通知列表
     */
    ResponseEntity<?> listNotifications(Long userId, NotificationListRequest request);

    /**
     * 新增创建工单通知，并推送给指定部门
     */
    void addNewTaskNotificationToDept(Long taskId, Long deptId);

    /**
     * 转移工单通知，并推送给指定用户
     */
    void addTransferTaskNotification(Long taskId, Long fromUserId, Long toUserId);

    /**
     * 完成工单通知，并推送给指定部门
     */
    void addCompleteTaskNotificationToDept(Long taskId);


    /**
     * 工单催办提醒通知，并推送给指定用户
     */
    void addReminderTaskNotification(Long taskId);
}
