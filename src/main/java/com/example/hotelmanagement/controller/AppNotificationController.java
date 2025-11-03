package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireAppToken;
import com.example.hotelmanagement.model.request.NotificationListRequest;
import com.example.hotelmanagement.service.HotelNotificationService;
import com.example.hotelmanagement.util.AppContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * App端通知Controller
 * 使用 @RequireAppToken 注解进行Token校验
 * 复用 HotelNotificationService 的业务逻辑
 */
@Slf4j
@RestController
@RequireAppToken
@CrossOrigin
@RequestMapping("/app/notification")
public class AppNotificationController {

    @Resource
    private HotelNotificationService notificationService;

    /**
     * 获取通知列表（游标分页）
     */
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody(required = false) NotificationListRequest request) {
        log.info("App端获取通知列表 - userId: {}, lastNotificationId: {}", 
                AppContext.getUserId(), 
                request != null ? request.getLastNotificationId() : null);
        return notificationService.listNotifications(AppContext.getUserId(), request);
    }
}

