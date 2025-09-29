package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.TaskOperateRecordRequest;
import org.springframework.http.ResponseEntity;

public interface HotelTaskOperateRecordService {

    ResponseEntity<?> queryByTaskId(TaskOperateRecordRequest request);
}
