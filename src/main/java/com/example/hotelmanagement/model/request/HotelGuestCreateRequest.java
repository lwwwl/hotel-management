package com.example.hotelmanagement.model.request;

import lombok.Data;
import java.sql.Timestamp;

/**
 * 客人创建请求对象
 */
@Data
public class HotelGuestCreateRequest {
    private String guestName;
    private String roomName;
    private String phoneSuffix;
}