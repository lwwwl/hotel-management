package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.service.HotelTaskService;
import com.example.hotelmanagement.util.UserContext;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    @Resource
    private HotelTaskService taskService;

    @PostMapping("/list")
    public ResponseEntity<?> getTaskList(
            @RequestBody TaskListRequest request) {
        return taskService.getTaskList(UserContext.getUserId(), request);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getTaskDetail(
            @RequestBody TaskDetailRequest request) {
        return taskService.getTaskDetail(UserContext.getUserId(), request);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(
            @RequestBody TaskCreateRequest request) {
        return taskService.createTask(UserContext.getUserId(), request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTask(
            @RequestBody TaskUpdateRequest request) {
        return taskService.updateTask(UserContext.getUserId(), request);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTask(
            @RequestBody TaskDeleteRequest request) {
        return taskService.deleteTask(UserContext.getUserId(), request);
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claimTask(
            @RequestBody TaskClaimRequest request) {
        return taskService.claimTask(UserContext.getUserId(), request);
    }

    @PostMapping("/add-executor")
    public ResponseEntity<?> addExecutor(
            @RequestBody TaskAddExecutorRequest request) {
        return taskService.addExecutor(UserContext.getUserId(), request);
    }

    @PostMapping("/transfer-executor")
    public ResponseEntity<?> transferExecutor(
            @RequestBody TaskTransferExecutorRequest request) {
        return taskService.transferExecutor(UserContext.getUserId(), request);
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatus(
            @RequestBody TaskChangeStatusRequest request) {
        return taskService.changeStatus(UserContext.getUserId(), request);
    }

    @PostMapping("/reminder")
    public ResponseEntity<?> sendReminder(
            @RequestBody TaskReminderRequest request) {
        return taskService.sendReminder(UserContext.getUserId(), request);
    }

    @PostMapping("/total-count")
    public ResponseEntity<?> getTotalCount() {
        return taskService.getTotalCount(UserContext.getUserId());
    }

    @PostMapping("/")
    public ResponseEntity<?> getTaskSLA(
            @RequestBody TaskSLARequest request) {
        return taskService.getTaskSLA(UserContext.getUserId());
    }
} 