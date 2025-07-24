package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.HotelTaskService;
import com.example.hotelmanagement.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HotelTaskServiceImpl implements HotelTaskService {

    private final HttpClientUtil httpClientUtil;
    private static final String USER_ID_HEADER = "X-User-Id";

    @Autowired
    public HotelTaskServiceImpl(HttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
    }
    
    private Map<String, String> createUserIdHeader(Long userId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(USER_ID_HEADER, userId.toString());
        return headers;
    }

    @Override
    public ResponseEntity<?> getTaskList(Long userId, TaskListRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/list", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> getTaskDetail(Long userId, TaskDetailRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/detail", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> createTask(Long userId, TaskCreateRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/create", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> updateTask(Long userId, TaskUpdateRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/update", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> deleteTask(Long userId, TaskDeleteRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/delete", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> claimTask(Long userId, TaskClaimRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/claim", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> addExecutor(Long userId, TaskAddExecutorRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/executor/add", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> transferExecutor(Long userId, TaskTransferExecutorRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/executor/transfer", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> changeStatus(Long userId, TaskChangeStatusRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/status", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> sendReminder(Long userId, TaskReminderRequest request) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/reminder", request, headers, ApiResponse.class);
    }

    @Override
    public ResponseEntity<?> getTaskSLA(Long userId) {
        Map<String, String> headers = createUserIdHeader(userId);
        return httpClientUtil.post("/task/sla", null, headers, ApiResponse.class);
    }
}
