package com.example.hotelmanagement.model.request;

import lombok.Data;

@Data
public class EventListRequest {
    private int page = 1;
    private int pageSize = 10;
}
