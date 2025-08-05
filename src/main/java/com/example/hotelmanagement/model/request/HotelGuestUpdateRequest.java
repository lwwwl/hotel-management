package com.example.hotelmanagement.model.request;

import lombok.Data;
import java.sql.Timestamp;

/**
 * 客人更新请求对象
 */
@Data
public class HotelGuestUpdateRequest {
    private Long guestId;
    private Boolean verify;
}