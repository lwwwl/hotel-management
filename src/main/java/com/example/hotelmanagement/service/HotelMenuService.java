package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.MenuListRequest;

public interface HotelMenuService {
    ResponseEntity<?> listMenus(MenuListRequest request);
}


