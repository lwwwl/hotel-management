package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.dao.entity.HotelEvent;
import com.example.hotelmanagement.model.request.EventListRequest;
import com.example.hotelmanagement.model.request.EventMetricsRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.EventListResponse;
import com.example.hotelmanagement.model.response.EventMetricsResponse;
import com.example.hotelmanagement.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/event")
public class EventController {

    @Resource
    private EventService eventService;

    @PostMapping("/list")
    public ResponseEntity<?> listEvents(@RequestBody EventListRequest request) {
        Page<HotelEvent> eventPage = eventService.list(request.getPage(), request.getPageSize());
        EventListResponse responseData = EventListResponse.from(eventPage);
        return ResponseEntity.ok(ApiResponse.success(responseData));
    }

    @PostMapping("/count")
    public ResponseEntity<?> countEvents() {
        long count = eventService.count();
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PostMapping("/metrics")
    public ResponseEntity<?> getMetrics(@RequestBody EventMetricsRequest request) {
        long count = eventService.getMetrics(request);
        return ResponseEntity.ok(ApiResponse.success(new EventMetricsResponse(count)));
    }
}
