package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.RoomCreateRequest;
import com.example.hotelmanagement.model.request.RoomDeleteRequest;
import com.example.hotelmanagement.model.request.RoomDetailRequest;
import com.example.hotelmanagement.model.request.RoomListRequest;
import com.example.hotelmanagement.model.request.RoomUpdateRequest;

public interface HotelRoomService {

    /** 创建房间 */
    ResponseEntity<?> create(RoomCreateRequest request);

    /** 更新房间 */
    ResponseEntity<?> update(RoomUpdateRequest request);

    /** 删除房间 */
    ResponseEntity<?> delete(RoomDeleteRequest request);

    /** 房间详情 */
    ResponseEntity<?> detail(RoomDetailRequest request);

    /** 房间列表（全量，不分页） */
    ResponseEntity<?> list(RoomListRequest request);
}


