package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

public interface WebService {

    ResponseEntity<?> getUserInfo(Long userId);

    ResponseEntity<?> getRouters();
}
