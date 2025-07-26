package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskChangeStatusRequest implements Serializable {
    private Long taskId;
    private String newTaskStatus;
} 