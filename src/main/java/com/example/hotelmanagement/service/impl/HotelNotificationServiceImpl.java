package com.example.hotelmanagement.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.hotelmanagement.dao.entity.HotelTask;
import com.example.hotelmanagement.dao.entity.HotelTaskNotification;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.entity.HotelUserDepartment;
import com.example.hotelmanagement.dao.repository.HotelTaskNotificationRepository;
import com.example.hotelmanagement.dao.repository.HotelTaskRepository;
import com.example.hotelmanagement.dao.repository.HotelUserDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
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

    @Resource
    private HotelUserDepartmentRepository hotelUserDepartmentRepository;

    @Resource
    private HotelTaskRepository taskRepository;

    @Resource
    private HotelUserRepository hotelUserRepository;

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

    @Override
    public void addNewTaskNotificationToDept(Long taskId, Long deptId) {
        if (taskId == null || deptId == null) {
            return;
        }
        List<HotelUserDepartment> userDeptList = hotelUserDepartmentRepository.findByDeptId(deptId);
        if (CollectionUtils.isEmpty(userDeptList)) {
            return;
        }
        HotelTask task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return;
        }
        List<HotelTaskNotification> notifications = new ArrayList<>();
        for (HotelUserDepartment userDept : userDeptList) {
            HotelTaskNotification notification = new HotelTaskNotification();
            notification.setUserId(userDept.getUserId());
            notification.setTitle("新任务分配");
            notification.setBody(task.getTitle());
            notification.setTaskId(taskId);
            notification.setNotificationType("info");
            notification.setAlreadyRead((short) 0);
            notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
            notification.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            notifications.add(notification);
        }
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void addTransferTaskNotification(Long taskId, Long fromUserId, Long toUserId) {
        HotelTask task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return;
        }
        HotelUser user = hotelUserRepository.findById(fromUserId).orElse(null);
        if (user == null) {
            return;
        }
        HotelTaskNotification notification = new HotelTaskNotification();
        notification.setUserId(toUserId);
        notification.setTitle(user.getDisplayName() + " 将任务转移给您");
        notification.setBody(task.getTitle());
        notification.setTaskId(taskId);
        notification.setNotificationType("info");
        notification.setAlreadyRead((short) 0);
        notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
        notification.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        notificationRepository.save(notification);
    }

    @Override
    public void addCompleteTaskNotificationToDept(Long taskId) {
        HotelTask task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return;
        }
        if (task.getDeptId() == null) {
            return;
        }
        List<HotelUserDepartment> userDeptList = hotelUserDepartmentRepository.findByDeptId(task.getDeptId());
        if (CollectionUtils.isEmpty(userDeptList)) {
            return;
        }
        HotelUser executor = hotelUserRepository.findById(task.getExecutorUserId()).orElse(null);
        if (executor == null) {
            return;
        }
        List<HotelTaskNotification> notifications = new ArrayList<>();
        for (HotelUserDepartment userDept : userDeptList) {
            HotelTaskNotification notification = new HotelTaskNotification();
            notification.setUserId(userDept.getUserId());
            notification.setTitle(executor.getDisplayName() + " 任务完成");
            notification.setBody(task.getTitle());
            notification.setTaskId(taskId);
            notification.setNotificationType("info");
            notification.setAlreadyRead((short) 0);
            notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
            notification.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            notifications.add(notification);
        }
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void addReminderTaskNotification(Long taskId) {
        HotelTask task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return;
        }
        // executor存在时，给executor发通知，不存在时，给工单所属部门所有用户发通知
        List<Long> sendToUserIds = new ArrayList<>();
        if (task.getExecutorUserId() != null) {
            sendToUserIds.add(task.getExecutorUserId());
        } else {
            if (task.getDeptId() == null) {
                return;            }
            // 发给工单所属部门所有用户
            List<HotelUserDepartment> userDeptList = hotelUserDepartmentRepository.findByDeptId(task.getDeptId());
            if (CollectionUtils.isEmpty(userDeptList)) {
                return;
            }
            for (HotelUserDepartment userDept : userDeptList) {
                sendToUserIds.add(userDept.getUserId());
            }
        }
        
        List<HotelTaskNotification> notifications = new ArrayList<>();
        for (Long userId : sendToUserIds) {
            HotelTaskNotification notification = new HotelTaskNotification();
            notification.setUserId(userId);
            notification.setTitle("任务催办提醒");
            notification.setBody(task.getTitle());
            notification.setTaskId(taskId);
            notification.setNotificationType("info");
            notification.setAlreadyRead((short) 0);
            notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
            notification.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            notifications.add(notification);
        }
        if (CollectionUtils.isEmpty(notifications)) {
            return;
        }
        notificationRepository.saveAll(notifications);
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
