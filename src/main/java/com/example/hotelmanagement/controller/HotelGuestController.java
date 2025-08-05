package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireGuestId;
import com.example.hotelmanagement.model.request.HotelGuestCreateRequest;
import com.example.hotelmanagement.model.request.HotelGuestUpdateRequest;
import com.example.hotelmanagement.model.request.HotelGuestDeleteRequest;
import com.example.hotelmanagement.model.request.HotelGuestDetailRequest;
import com.example.hotelmanagement.service.HotelGuestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequireGuestId
@CrossOrigin
@RequestMapping("/chat-guest")
public class HotelGuestController {

    @Resource
    private HotelGuestService hotelGuestService;

    @PostMapping("/create")
    public ResponseEntity<?> createGuest(@RequestBody HotelGuestCreateRequest request) {
        return hotelGuestService.createGuest(request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateGuest(@RequestBody HotelGuestUpdateRequest request) {
        return hotelGuestService.updateGuest(request);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteGuest(@RequestBody HotelGuestDeleteRequest request) {
        return hotelGuestService.deleteGuest(request);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getGuestById(@RequestBody HotelGuestDetailRequest request) {
        return hotelGuestService.getGuestById(request);
    }
}
