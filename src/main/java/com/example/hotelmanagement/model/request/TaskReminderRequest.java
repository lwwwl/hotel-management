package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskReminderRequest implements Serializable {
    private Long taskId;
} 