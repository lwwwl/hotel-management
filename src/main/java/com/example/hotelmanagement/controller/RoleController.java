package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
}
