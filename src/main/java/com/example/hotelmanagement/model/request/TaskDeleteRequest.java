package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskDeleteRequest implements Serializable {
    private Long taskId;
} 