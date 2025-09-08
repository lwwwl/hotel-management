package com.example.hotelmanagement.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.HotelRoom;
import com.example.hotelmanagement.dao.repository.HotelRoomRepository;
import com.example.hotelmanagement.model.request.RoomCreateRequest;
import com.example.hotelmanagement.model.request.RoomDeleteRequest;
import com.example.hotelmanagement.model.request.RoomDetailRequest;
import com.example.hotelmanagement.model.request.RoomListRequest;
import com.example.hotelmanagement.model.request.RoomUpdateRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.HotelRoomService;

@Service
public class HotelRoomServiceImpl implements HotelRoomService {

    @Autowired
    private HotelRoomRepository roomRepository;

    @Override
    public ResponseEntity<?> create(RoomCreateRequest request) {
        try {
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "创建房间失败", "房间名称不能为空"));
            }
            Optional<HotelRoom> existing = roomRepository.findByName(request.getName());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "创建房间失败", "房间名称已存在"));
            }
            HotelRoom room = new HotelRoom();
            room.setName(request.getName());
            room.setActive(request.getActive() == null ? Short.valueOf((short)1) : request.getActive());
            HotelRoom saved = roomRepository.save(room);
            return ResponseEntity.ok(ApiResponse.success(saved.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "创建房间失败", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> update(RoomUpdateRequest request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "更新房间失败", "房间ID不能为空"));
            }
            Optional<HotelRoom> optional = roomRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "更新房间失败", "房间不存在"));
            }
            HotelRoom room = optional.get();
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                // 名称重复校验
                Optional<HotelRoom> nameDup = roomRepository.findByName(request.getName());
                if (nameDup.isPresent() && !Objects.equals(nameDup.get().getId(), room.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.error(400, "更新房间失败", "房间名称已存在"));
                }
                room.setName(request.getName());
            }
            if (request.getActive() != null) {
                room.setActive(request.getActive());
            }
            roomRepository.save(room);
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "更新房间失败", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> delete(RoomDeleteRequest request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "删除房间失败", "房间ID不能为空"));
            }
            Optional<HotelRoom> optional = roomRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "删除房间失败", "房间不存在"));
            }
            roomRepository.deleteById(request.getId());
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "删除房间失败", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> detail(RoomDetailRequest request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "获取房间详情失败", "房间ID不能为空"));
            }
            Optional<HotelRoom> optional = roomRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "获取房间详情失败", "房间不存在"));
            }
            return ResponseEntity.ok(ApiResponse.success(optional.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取房间详情失败", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> list(RoomListRequest request) {
        try {
            List<HotelRoom> result;
            if (request != null) {
                if (request.getActive() != null) {
                    result = roomRepository.findByActiveOrderByNameAsc(request.getActive());
                } else if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
                    result = roomRepository.searchByName(request.getKeyword().trim());
                } else {
                    result = roomRepository.findAll();
                }
            } else {
                result = roomRepository.findAll();
            }
            return ResponseEntity.ok(ApiResponse.success(result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取房间列表失败", e.getMessage()));
        }
    }
}


