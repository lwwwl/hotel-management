package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.NotificationListRequest;

public interface HotelNotificationService {

    /**
     * 查询用户通知列表
     */
    ResponseEntity<?> listNotifications(Long userId, NotificationListRequest request);
}
