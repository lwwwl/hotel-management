package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.RoleCreateRequest;
import com.example.hotelmanagement.model.request.RoleDeleteRequest;
import com.example.hotelmanagement.model.request.RoleDetailRequest;
import com.example.hotelmanagement.model.request.RoleListRequest;
import com.example.hotelmanagement.model.request.RoleUpdateMenuRequest;
import com.example.hotelmanagement.model.request.RoleUpdateRequest;
import com.example.hotelmanagement.model.request.RoleUpdateUserRequest;

public interface HotelRoleService {
    ResponseEntity<?> createRole(RoleCreateRequest request);

    ResponseEntity<?> updateRole(RoleUpdateRequest request);
    
    ResponseEntity<?> updateRoleUser(RoleUpdateUserRequest request);
    
    ResponseEntity<?> updateRoleMenu(RoleUpdateMenuRequest request);

    ResponseEntity<?> deleteRole(RoleDeleteRequest request);

    ResponseEntity<?> listRoles(RoleListRequest request);
    
    ResponseEntity<?> getRoleDetail(RoleDetailRequest request);
}


