package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.*;
import org.springframework.http.ResponseEntity;


public interface HotelTaskService {
    /**
     * 获取工单列表
     */
    ResponseEntity<?> getTaskList(Long userId, TaskListRequest request);

    /**
     * 获取工单详情
     */
    ResponseEntity<?> getTaskDetail(Long userId, TaskDetailRequest request);

    /**
     * 创建工单
     */
    ResponseEntity<?> createTask(Long userId, TaskCreateRequest request);

    /**
     * 更新工单信息
     */
    ResponseEntity<?> updateTask(Long userId, TaskUpdateRequest request);

    /**
     * 删除工单
     */
    ResponseEntity<?> deleteTask(Long userId, TaskDeleteRequest request);

    /**
     * 认领工单
     */
    ResponseEntity<?> claimTask(Long userId, TaskClaimRequest request);

    /**
     * 添加执行人
     */
    ResponseEntity<?> addExecutor(Long userId, TaskAddExecutorRequest request);

    /**
     * 转移执行人
     */
    ResponseEntity<?> transferExecutor(Long userId, TaskTransferExecutorRequest request);

    /**
     * 变更工单状态
     */
    ResponseEntity<?> changeStatus(Long userId, TaskChangeStatusRequest request);

    /**
     * 发送工单催办
     */
    ResponseEntity<?> sendReminder(Long userId, TaskReminderRequest request);

    /**
     * 获取工单总数
     */
    ResponseEntity<?> getTotalCount(Long userId);

    /**
     * 获取工单SLA看板数据
     */
    ResponseEntity<?> getTaskSLA(Long userId);
}