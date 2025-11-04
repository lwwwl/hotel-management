package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireAppToken;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.model.request.UserDetailRequest;
import com.example.hotelmanagement.model.request.UserUpdatePasswordRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.HotelUserService;
import com.example.hotelmanagement.service.LdapService;
import com.example.hotelmanagement.util.AppContext;
import com.example.hotelmanagement.util.PasswordUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * App端用户Controller
 * 使用 @RequireAppToken 注解进行Token校验
 * 提供用户详情查询和修改密码功能
 */
@Slf4j
@RestController
@RequireAppToken
@CrossOrigin
@RequestMapping("/app/user")
public class AppUserController {

    @Resource
    private HotelUserService userService;

    @Resource
    private HotelUserRepository userRepository;

    @Resource
    private LdapService ldapService;

    /**
     * 获取当前登录用户的详细信息
     */
    @PostMapping("/detail")
    public ResponseEntity<?> getUserDetail() {
        log.info("App端获取用户详情 - userId: {}", AppContext.getUserId());
        
        try {
            UserDetailRequest request = new UserDetailRequest();
            request.setUserId(AppContext.getUserId());
            return userService.getUserDetail(request);
        } catch (Exception e) {
            log.error("获取用户详情异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取用户详情失败", e.getMessage()));
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/update-password")
    @Transactional
    public ResponseEntity<?> updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        log.info("App端修改密码 - userId: {}", AppContext.getUserId());
        
        try {
            // 1. 参数校验
            if (!StringUtils.hasText(request.getOldPassword())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "参数错误", "旧密码不能为空"));
            }
            if (!StringUtils.hasText(request.getNewPassword())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "参数错误", "新密码不能为空"));
            }
            if (request.getNewPassword().length() < 6) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "参数错误", "新密码长度不能少于6位"));
            }
            if (request.getOldPassword().equals(request.getNewPassword())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "参数错误", "新密码不能与旧密码相同"));
            }

            // 2. 查询用户
            Long userId = AppContext.getUserId();
            Optional<HotelUser> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "用户不存在", "未找到指定用户"));
            }

            HotelUser user = userOptional.get();

            // 3. 验证旧密码
            if (!PasswordUtil.checkPassword(request.getOldPassword(), user.getPassword())) {
                log.warn("修改密码失败：旧密码错误 - userId: {}", userId);
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "修改密码失败", "旧密码错误"));
            }

            // 4. 更新密码
            user.setPassword(PasswordUtil.hashPassword(request.getNewPassword()));
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            HotelUser savedUser = userRepository.save(user);

            // 5. 同步到LDAP
            try {
                ldapService.updateUser(savedUser);
            } catch (Exception e) {
                log.error("同步密码到LDAP失败 - userId: {}", userId, e);
                // LDAP同步失败不影响数据库修改，只记录日志
            }

            log.info("修改密码成功 - userId: {}", userId);
            return ResponseEntity.ok(ApiResponse.success(true));

        } catch (Exception e) {
            log.error("修改密码异常 - userId: {}", AppContext.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "修改密码失败", e.getMessage()));
        }
    }
}

