package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireGuestId;
import com.example.hotelmanagement.model.request.QuickMenuListRequest;
import com.example.hotelmanagement.service.HotelQuickMenuService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireGuestId
@CrossOrigin
@RequestMapping("/quickMenu-guest")
public class QuickMenuForGuestController {

    @Resource
    private HotelQuickMenuService quickMenuService;

    /** 列表，返回全量快捷菜单 */
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody QuickMenuListRequest request) {
        return quickMenuService.list(request);
    }
}
