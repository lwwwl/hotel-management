package com.example.hotelmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.response.ResponseResult;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/web")
public class WebController {

    // getRouter   下发所有菜单和子菜单信息
    @GetMapping("/getRouter")
    public ResponseEntity<?> getRouter() {
        return ResponseEntity.ok(ResponseResult.success());
    }

    // getUserInfo    下发用户信息
    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok(ResponseResult.success());
    }
}
