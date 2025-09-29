package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.TaskOperateRecordRequest;
import com.example.hotelmanagement.service.HotelTaskOperateRecordService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequireUserId
@RequestMapping("/task/operate-record")
public class TaskOperateRecordController {

    @Resource
    private HotelTaskOperateRecordService hotelTaskOperateRecordService;

    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody TaskOperateRecordRequest request) {
        return hotelTaskOperateRecordService.queryByTaskId(request);
    }
}
