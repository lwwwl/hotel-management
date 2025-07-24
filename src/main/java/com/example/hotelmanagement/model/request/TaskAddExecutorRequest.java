package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskAddExecutorRequest implements Serializable {
    private Long taskId;
    private Long executorUserId;
} 