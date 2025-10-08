package com.example.hotelmanagement.model.request;

import lombok.Data;

@Data
public class EventMetricsRequest {

    private String eventType;

    private String eventSubType;

    private String timeRange = "all"; // Default to "all"
}
