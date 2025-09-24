package com.example.hotelmanagement.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.HotelTaskNotification;
import com.example.hotelmanagement.dao.repository.HotelTaskNotificationRepository;
import com.example.hotelmanagement.model.request.NotificationListRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.NotificationListResponse;
import com.example.hotelmanagement.service.HotelNotificationService;

import jakarta.annotation.Resource;

@Service
public class HotelNotificationServiceImpl implements HotelNotificationService {

    private static final int DEFAULT_PAGE_SIZE = 100;

    @Resource
    private HotelTaskNotificationRepository notificationRepository;

    @Override
    public ResponseEntity<?> listNotifications(Long userId, NotificationListRequest request) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "请求参数错误", "用户ID不能为空"));
        }

        try {
            NotificationListRequest actualRequest = request != null ? request : new NotificationListRequest();
            int size = (actualRequest.getSize() == null || actualRequest.getSize() <= 0)
                    ? DEFAULT_PAGE_SIZE
                    : actualRequest.getSize();

            int querySize = size + 1;
            List<HotelTaskNotification> entities = notificationRepository.findByUserIdWithCursorPagination(
                    userId,
                    actualRequest.getLastNotificationId(),
                    querySize);

            boolean hasMore = entities.size() > size;
            if (hasMore) {
                entities = entities.subList(0, size);
            }

            NotificationListResponse response = buildResponse(entities, size, hasMore);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取通知列表失败", e.getMessage()));
        }
    }

    private NotificationListResponse buildResponse(List<HotelTaskNotification> entities, int size, boolean hasMore) {
        List<NotificationListResponse.NotificationItem> items = new ArrayList<>();
        Long lastId = null;

        for (HotelTaskNotification entity : entities) {
            NotificationListResponse.NotificationItem item = new NotificationListResponse.NotificationItem();
            item.setId(entity.getId());
            item.setTitle(entity.getTitle());
            item.setBody(entity.getBody());
            item.setTaskId(entity.getTaskId());
            item.setNotificationType(entity.getNotificationType());
            item.setAlreadyRead(entity.getAlreadyRead());
            item.setCreateTime(toMillis(entity.getCreateTime()));
            items.add(item);
            lastId = entity.getId();
        }

        NotificationListResponse response = new NotificationListResponse();
        response.setNotifications(items);
        response.setHasMore(hasMore);
        response.setLastNotificationId(lastId);
        response.setSize(size);
        return response;
    }

    private Long toMillis(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }
}
