package com.example.hotelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.UserCreateRequest;
import com.example.hotelmanagement.model.request.UserDeleteRequest;
import com.example.hotelmanagement.model.request.UserDetailRequest;
import com.example.hotelmanagement.model.request.UserLockRequest;
import com.example.hotelmanagement.model.request.UserSearchRequest;
import com.example.hotelmanagement.model.request.UserUpdateBasicRequest;
import com.example.hotelmanagement.model.request.UserUpdateRequest;
import com.example.hotelmanagement.service.HotelUserService;
import com.example.hotelmanagement.util.UserContext;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HotelUserService userService;

    /**
     * 用户详情接口
     * @param request 包含用户ID的请求
     * @return 用户详情响应
     */
    @PostMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestBody UserDetailRequest request) {
        return userService.getUserDetail(request);
    }

    /**
     * 添加用户接口
     * @param request 包含用户信息的创建请求
     * @return 新创建用户的ID
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    /**
     * 编辑用户接口
     * @param request 包含用户更新信息的请求
     * @return 更新结果
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        return userService.updateUser(request);
    }

    @PostMapping("/update-basic")
    public ResponseEntity<?> updateUserBasicInfo(@RequestBody UserUpdateBasicRequest request) {
        return userService.updateUserBasicInfo(request, UserContext.getUserId());
    }

    /**
     * 删除用户接口
     * @param request 包含用户ID的请求
     * @return 删除结果
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteRequest request) {
        return userService.deleteUser(request);
    }

    /**
     * 锁定/解锁用户接口
     * @param request 包含用户ID的请求
     * @return lock或unlock状态
     */
    @PostMapping("/lock")
    public ResponseEntity<?> toggleUserLock(@RequestBody UserLockRequest request) {
        return userService.toggleUserLock(request);
    }

    /**
     * 搜索用户接口
     * @param request 包含搜索条件的请求
     * @return 搜索结果
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestBody UserSearchRequest request) {
        return userService.searchUsers(request);
    }
}
