package com.example.hotelmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.service.WebService;
import com.example.hotelmanagement.util.UserContext;

import jakarta.annotation.Resource;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/web")
public class WebController {

    @Resource
    private WebService webService;

    // getRouter   下发所有菜单和子菜单信息
    @GetMapping("/getRouter")
    public ResponseEntity<?> getRouter() {
        return webService.getRouters();
    }

    // getUserInfo    下发用户信息，除了用户user基本信息，还下发用户拥有的部门，角色，menu权限
    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo() {
        return webService.getUserInfo(UserContext.getUserId());
    }
}
