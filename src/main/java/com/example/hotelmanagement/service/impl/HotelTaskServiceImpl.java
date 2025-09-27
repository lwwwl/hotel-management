package com.example.hotelmanagement.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.model.request.TaskAddExecutorRequest;
import com.example.hotelmanagement.model.request.TaskChangeStatusRequest;
import com.example.hotelmanagement.model.request.TaskClaimRequest;
import com.example.hotelmanagement.model.request.TaskCreateRequest;
import com.example.hotelmanagement.model.request.TaskDeleteRequest;
import com.example.hotelmanagement.model.request.TaskDetailRequest;
import com.example.hotelmanagement.model.request.TaskListRequest;
import com.example.hotelmanagement.model.request.TaskReminderRequest;
import com.example.hotelmanagement.model.request.TaskTransferExecutorRequest;
import com.example.hotelmanagement.model.request.TaskUpdateRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.HotelNotificationService;
import com.example.hotelmanagement.service.HotelTaskService;
import com.example.hotelmanagement.util.HttpClientUtil;

import jakarta.annotation.Resource;

@Service
public class HotelTaskServiceImpl implements HotelTaskService {

    private static final Logger logger = LoggerFactory.getLogger(HotelTaskServiceImpl.class);
    private final HttpClientUtil httpClientUtil;
    private static final String USER_ID_HEADER = "X-User-Id";

    @Autowired
    public HotelTaskServiceImpl(HttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
    }

    @Resource
    private HotelNotificationService hotelNotificationService;
    
    private Map<String, String> createUserIdHeader(Long userId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(USER_ID_HEADER, userId.toString());
        return headers;
    }

    @Override
    public ResponseEntity<?> getTaskList(Long userId, TaskListRequest request) {
        String apiPath = "/task/list";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            // 如果远程服务正常返回了response，直接拷贝信息包装为新的response
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                // 没有正常返回response，返回请求失败的error
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getTaskDetail(Long userId, TaskDetailRequest request) {
        String apiPath = "/task/detail";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> createTask(Long userId, TaskCreateRequest request) {
        String apiPath = "/task/create";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                try {
                    ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
                    Long taskId = (Long) apiResponse.getData();
                    hotelNotificationService.addNewTaskNotificationToDept(taskId, request.getDeptId());
                } catch (Exception e) {
                    // ignore
                }
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateTask(Long userId, TaskUpdateRequest request) {
        String apiPath = "/task/update";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> deleteTask(Long userId, TaskDeleteRequest request) {
        String apiPath = "/task/delete";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> claimTask(Long userId, TaskClaimRequest request) {
        String apiPath = "/task/claim";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> addExecutor(Long userId, TaskAddExecutorRequest request) {
        String apiPath = "/task/add-executor";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> transferExecutor(Long userId, TaskTransferExecutorRequest request) {
        String apiPath = "/task/transfer-executor";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                try {
                    hotelNotificationService.addTransferTaskNotification(request.getTaskId(), userId, request.getNewExecutorUserId());
                } catch (Exception e) {
                    // ignore
                }
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> changeStatus(Long userId, TaskChangeStatusRequest request) {
        String apiPath = "/task/change-status";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                if ("completed".equals(request.getNewTaskStatus())) {
                    try {
                        hotelNotificationService.addCompleteTaskNotificationToDept(request.getTaskId());
                    } catch (Exception e) {
                        // ignore
                    }
                }
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> sendReminder(Long userId, TaskReminderRequest request) {
        String apiPath = "/task/reminder";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, request, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                try {
                    hotelNotificationService.addReminderTaskNotification(request.getTaskId());
                } catch (Exception e) {
                    // ignore
                }
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getTotalCount(Long userId) {
        String apiPath = "/task/total-count";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, null, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getTaskSLA(Long userId) {
        String apiPath = "/task/sla";
        logger.info("调用接口: {}", apiPath);
        
        Map<String, String> headers = createUserIdHeader(userId);
        try {
            ResponseEntity<?> response = httpClientUtil.post(apiPath, null, headers, ApiResponse.class);
            logResponse(response);
            
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", "远程服务无响应");
                return ResponseEntity.ok(errorResponse);
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ApiResponse<?> errorResponse = ApiResponse.error(500, "请求失败", e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("接口返回 - statusCode: {}, message: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? ((ApiResponse) response.getBody()).getMessage() : "null");
    }
}
