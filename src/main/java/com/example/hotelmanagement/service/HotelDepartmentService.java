package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.*;
import org.springframework.http.ResponseEntity;

public interface HotelDepartmentService {
    ResponseEntity<?> getDeptSelectList(DeptSelectListRequest request);

    ResponseEntity<?> getDeptList(DeptListRequest request);

    ResponseEntity<?> getDeptDetail(DeptDetailRequest request);

    ResponseEntity<?> createDept(DeptCreateRequest request);

    ResponseEntity<?> updateDept(DeptUpdateRequest request);

    ResponseEntity<?> deleteDept(DeptDeleteRequest request);

    ResponseEntity<?> addDeptUser(DeptAddUserRequest request);

    ResponseEntity<?> removeDeptUser(DeptRemoveUserRequest request);
} 