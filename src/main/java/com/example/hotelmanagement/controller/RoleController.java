package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.RoleCreateRequest;
import com.example.hotelmanagement.model.request.RoleDeleteRequest;
import com.example.hotelmanagement.model.request.RoleListRequest;
import com.example.hotelmanagement.model.request.RoleUpdateRequest;
import com.example.hotelmanagement.service.HotelRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private HotelRoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody RoleCreateRequest request) {
        return roleService.createRole(request);
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateRole(@RequestBody RoleUpdateRequest request) {
        return roleService.updateRole(request);
    }
    @PostMapping("/delete")
    public ResponseEntity<?> deleteRole(@RequestBody RoleDeleteRequest request) {
        return roleService.deleteRole(request);
    }
    @PostMapping("/list")
    public ResponseEntity<?> listRoles(@RequestBody RoleListRequest request) {
        return roleService.listRoles(request);
    }
}
