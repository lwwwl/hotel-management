package com.example.hotelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.RoomCreateRequest;
import com.example.hotelmanagement.model.request.RoomDeleteRequest;
import com.example.hotelmanagement.model.request.RoomDetailRequest;
import com.example.hotelmanagement.model.request.RoomListRequest;
import com.example.hotelmanagement.model.request.RoomUpdateRequest;
import com.example.hotelmanagement.service.HotelRoomService;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private HotelRoomService roomService;

    /** 创建房间 */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RoomCreateRequest request) {
        return roomService.create(request);
    }

    /** 更新房间 */
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody RoomUpdateRequest request) {
        return roomService.update(request);
    }

    /** 删除房间 */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody RoomDeleteRequest request) {
        return roomService.delete(request);
    }

    /** 房间详情 */
    @PostMapping("/detail")
    public ResponseEntity<?> detail(@RequestBody RoomDetailRequest request) {
        return roomService.detail(request);
    }

    /** 房间列表（全量，不分页） */
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody(required = false) RoomListRequest request) {
        return roomService.list(request);
    }
}


