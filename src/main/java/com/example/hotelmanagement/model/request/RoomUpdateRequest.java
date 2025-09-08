package com.example.hotelmanagement.model.request;

import lombok.Data;

@Data
public class RoomUpdateRequest {
    /** 房间ID */
    private Long id;
    /** 房间名称 */
    private String name;
    /** 是否有效 0-无效 1-有效 */
    private Short active;
}


