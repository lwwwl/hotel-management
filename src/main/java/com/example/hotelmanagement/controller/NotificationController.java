package com.example.hotelmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.NotificationListRequest;
import com.example.hotelmanagement.service.HotelNotificationService;
import com.example.hotelmanagement.util.UserContext;

import jakarta.annotation.Resource;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {

    @Resource
    private HotelNotificationService notificationService;

    /**
     * 通知列表（游标分页）
     */
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody(required = false) NotificationListRequest request) {
        return notificationService.listNotifications(UserContext.getUserId(), request);
    }
}
