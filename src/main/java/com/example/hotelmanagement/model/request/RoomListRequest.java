package com.example.hotelmanagement.model.request;

import lombok.Data;

@Data
public class RoomListRequest {
    /** 仅查询有效房间，可为空表示全部 */
    private Short active;
    /** 名称关键字，可为空表示不限 */
    private String keyword;
}


