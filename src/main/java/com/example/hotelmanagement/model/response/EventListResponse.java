package com.example.hotelmanagement.model.response;

import com.example.hotelmanagement.dao.entity.HotelEvent;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class EventListResponse {
    private List<HotelEvent> events;
    private long total;
    private int page;
    private int pageSize;
    private int totalPages;
    private boolean hasMore;

    public static EventListResponse from(Page<HotelEvent> page) {
        EventListResponse response = new EventListResponse();
        response.setEvents(page.getContent());
        response.setTotal(page.getTotalElements());
        response.setPage(page.getNumber() + 1); // Frontend expects 1-based page number
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setHasMore(page.hasNext());
        return response;
    }
}
