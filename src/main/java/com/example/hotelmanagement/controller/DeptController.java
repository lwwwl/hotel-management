package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.service.HotelDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private HotelDepartmentService departmentService;

    /**
     * 部门选择列表
     */
    @PostMapping("/select-list")
    public ResponseEntity<?> getDeptSelectList(@RequestBody DeptSelectListRequest request) {
        return departmentService.getDeptSelectList(request);
    }

    /**
     * 部门列表
     */
    @PostMapping("/list")
    public ResponseEntity<?> getDeptList(@RequestBody DeptListRequest request) {
        return departmentService.getDeptList(request);
    }

    /**
     * 部门详情
     */
    @PostMapping("/detail")
    public ResponseEntity<?> getDeptDetail(@RequestBody DeptDetailRequest request) {
        return departmentService.getDeptDetail(request);
    }

    /**
     * 创建部门
     */
    @PostMapping("/create")
    public ResponseEntity<?> createDept(@RequestBody DeptCreateRequest request) {
        return departmentService.createDept(request);
    }

    /**
     * 更新部门
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateDept(@RequestBody DeptUpdateRequest request) {
        return departmentService.updateDept(request);
    }

    /**
     * 删除部门
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteDept(@RequestBody DeptDeleteRequest request) {
        return departmentService.deleteDept(request);
    }

    /**
     * 新增成员
     */
    @PostMapping("/add-user")
    public ResponseEntity<?> addDeptUser(@RequestBody DeptAddUserRequest request) {
        return departmentService.addDeptUser(request);
    }

    /**
     * 删除成员
     */
    @PostMapping("/remove-user")
    public ResponseEntity<?> removeDeptUser(@RequestBody DeptRemoveUserRequest request) {
        return departmentService.removeDeptUser(request);
    }
}
