package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dao.entity.HotelEvent;
import com.example.hotelmanagement.enums.EventSubType;
import com.example.hotelmanagement.enums.EventType;
import com.example.hotelmanagement.model.request.EventMetricsRequest;
import org.springframework.data.domain.Page;

public interface EventService {
    Page<HotelEvent> list(int page, int pageSize);

    long count();

    void recordEvent(EventType type, EventSubType subType, String content);

    long getMetrics(EventMetricsRequest request);
}
