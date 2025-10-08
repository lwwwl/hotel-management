package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.UserCreateRequest;
import com.example.hotelmanagement.model.request.UserDeleteRequest;
import com.example.hotelmanagement.model.request.UserDetailRequest;
import com.example.hotelmanagement.model.request.UserLockRequest;
import com.example.hotelmanagement.model.request.UserSearchRequest;
import com.example.hotelmanagement.model.request.UserUpdateBasicRequest;
import com.example.hotelmanagement.model.request.UserUpdateRequest;

public interface HotelUserService {
    /**
     * 根据条件搜索用户
     * @param request 带有搜索条件的请求
     * @return 带有搜索结果的响应
     */
    ResponseEntity<?> searchUsers(UserSearchRequest request);
    
    /**
     * 获取指定用户的详细信息
     * @param request 包含用户ID的请求
     * @return 带有详细用户信息的响应
     */
    ResponseEntity<?> getUserDetail(UserDetailRequest request);
    
    /**
     * 创建新用户
     * @param request 包含新用户信息的请求
     * @return 创建的用户ID
     */
    ResponseEntity<?> createUser(UserCreateRequest request);
    
    /**
     * 更新现有用户
     * @param request 包含更新用户信息的请求
     * @return 如果更新成功返回true，否则返回false
     */
    ResponseEntity<?> updateUser(UserUpdateRequest request);
    
    /**
     * 删除用户
     * @param request 包含用户ID的请求
     * @return 如果删除成功返回true，否则返回false
     */
    ResponseEntity<?> deleteUser(UserDeleteRequest request);
    
    /**
     * 锁定或解锁用户账户
     * @param request 包含用户ID的请求
     * @return 如果用户被锁定返回"lock"，如果用户被解锁返回"unlock"
     */
    ResponseEntity<?> toggleUserLock(UserLockRequest request);

    /**
     * 将所有用户数据同步到LDAP
     */
    void syncAllUsersToLdap();
    /**
     * 更新当前用户的基本信息
     * @param request 包含要更新的字段
     * @param userId 当前用户ID
     * @return 如果更新成功返回true，否则返回false
     */
    ResponseEntity<?> updateUserBasicInfo(UserUpdateBasicRequest request, Long userId);
}
