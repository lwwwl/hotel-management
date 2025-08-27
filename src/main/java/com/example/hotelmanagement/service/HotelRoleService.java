package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.RoleCreateRequest;
import com.example.hotelmanagement.model.request.RoleDeleteRequest;
import com.example.hotelmanagement.model.request.RoleListRequest;
import com.example.hotelmanagement.model.request.RoleUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface HotelRoleService {
    ResponseEntity<?> createRole(RoleCreateRequest request);

    ResponseEntity<?> updateRole(RoleUpdateRequest request);

    ResponseEntity<?> deleteRole(RoleDeleteRequest request);

    ResponseEntity<?> listRoles(RoleListRequest request);
}


