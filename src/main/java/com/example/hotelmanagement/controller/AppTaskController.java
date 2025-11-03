package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireAppToken;
import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.service.HotelTaskService;
import com.example.hotelmanagement.util.AppContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * App端工单Controller
 * 使用 @RequireAppToken 注解进行Token校验
 * 复用 HotelTaskService 的业务逻辑
 */
@Slf4j
@RestController
@RequireAppToken
@CrossOrigin
@RequestMapping("/app/task")
public class AppTaskController {

    @Resource
    private HotelTaskService taskService;

    /**
     * 获取工单列表
     */
    @PostMapping("/list")
    public ResponseEntity<?> getTaskList(@RequestBody TaskListRequest request) {
        log.info("App端获取工单列表 - userId: {}, username: {}", 
                AppContext.getUserId(), AppContext.getUsername());
        return taskService.getTaskList(AppContext.getUserId(), request);
    }

    /**
     * 获取工单详情
     */
    @PostMapping("/detail")
    public ResponseEntity<?> getTaskDetail(@RequestBody TaskDetailRequest request) {
        log.info("App端获取工单详情 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.getTaskDetail(AppContext.getUserId(), request);
    }

    /**
     * 认领工单
     */
    @PostMapping("/claim")
    public ResponseEntity<?> claimTask(@RequestBody TaskClaimRequest request) {
        log.info("App端认领工单 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.claimTask(AppContext.getUserId(), request);
    }

    /**
     * 变更工单状态
     */
    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatus(@RequestBody TaskChangeStatusRequest request) {
        log.info("App端变更工单状态 - userId: {}, taskId: {}, newStatus: {}", 
                AppContext.getUserId(), request.getTaskId(), request.getNewTaskStatus());
        return taskService.changeStatus(AppContext.getUserId(), request);
    }

    /**
     * 创建工单
     */
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest request) {
        log.info("App端创建工单 - userId: {}", AppContext.getUserId());
        return taskService.createTask(AppContext.getUserId(), request);
    }

    /**
     * 更新工单
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskUpdateRequest request) {
        log.info("App端更新工单 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.updateTask(AppContext.getUserId(), request);
    }

    /**
     * 删除工单
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestBody TaskDeleteRequest request) {
        log.info("App端删除工单 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.deleteTask(AppContext.getUserId(), request);
    }

    /**
     * 添加执行人
     */
    @PostMapping("/add-executor")
    public ResponseEntity<?> addExecutor(@RequestBody TaskAddExecutorRequest request) {
        log.info("App端添加执行人 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.addExecutor(AppContext.getUserId(), request);
    }

    /**
     * 转移执行人
     */
    @PostMapping("/transfer-executor")
    public ResponseEntity<?> transferExecutor(@RequestBody TaskTransferExecutorRequest request) {
        log.info("App端转移执行人 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.transferExecutor(AppContext.getUserId(), request);
    }

    /**
     * 发送提醒
     */
    @PostMapping("/reminder")
    public ResponseEntity<?> sendReminder(@RequestBody TaskReminderRequest request) {
        log.info("App端发送提醒 - userId: {}, taskId: {}", 
                AppContext.getUserId(), request.getTaskId());
        return taskService.sendReminder(AppContext.getUserId(), request);
    }

    /**
     * 获取工单总数
     */
    @PostMapping("/total-count")
    public ResponseEntity<?> getTotalCount() {
        log.info("App端获取工单总数 - userId: {}", AppContext.getUserId());
        return taskService.getTotalCount(AppContext.getUserId());
    }

    /**
     * 获取工单SLA
     */
    @PostMapping("/sla")
    public ResponseEntity<?> getTaskSLA(@RequestBody TaskSLARequest request) {
        log.info("App端获取工单SLA - userId: {}", AppContext.getUserId());
        return taskService.getTaskSLA(AppContext.getUserId());
    }
}

