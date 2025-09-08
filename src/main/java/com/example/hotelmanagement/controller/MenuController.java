package com.example.hotelmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.MenuListRequest;
import com.example.hotelmanagement.service.HotelMenuService;

import jakarta.annotation.Resource;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private HotelMenuService menuService;

    @PostMapping("/list")
    public ResponseEntity<?> listMenus(@RequestBody(required = false) MenuListRequest request) {
        return menuService.listMenus(request == null ? new MenuListRequest() : request);
    }
}


