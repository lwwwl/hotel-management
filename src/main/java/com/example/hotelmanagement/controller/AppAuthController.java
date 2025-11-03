package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dao.entity.AuthToken;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.repository.AuthTokenRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.model.request.AppLoginRequest;
import com.example.hotelmanagement.model.response.AppLoginResponse;
import com.example.hotelmanagement.model.response.ResponseResult;
import com.example.hotelmanagement.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

/**
 * App端认证控制器
 * 提供登录和登出功能
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api")
public class AppAuthController {

    @Autowired
    private HotelUserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * App登录接口
     * @param request 登录请求（包含用户名、密码、设备信息）
     * @return 登录结果（包含token）
     */
    @PostMapping("/app-login")
    @Transactional
    public ResponseResult<AppLoginResponse> login(@RequestBody AppLoginRequest request) {
        try {
            // 1. 参数校验
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseResult.<AppLoginResponse>fail("用户名不能为空");
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseResult.<AppLoginResponse>fail("密码不能为空");
            }

            String username = request.getUsername().trim();
            String password = request.getPassword();

            // 2. 查询用户
            Optional<HotelUser> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                log.warn("登录失败：用户不存在 - {}", username);
                return ResponseResult.<AppLoginResponse>fail("用户名或密码错误");
            }

            HotelUser user = userOpt.get();

            // 3. 检查用户状态
            if (user.getActive() == null || user.getActive() != 1) {
                log.warn("登录失败：用户已被禁用 - {}", username);
                return ResponseResult.<AppLoginResponse>fail("该账号已被禁用，请联系管理员");
            }

            // 4. 验证密码
            if (!PasswordUtil.checkPassword(password, user.getPassword())) {
                log.warn("登录失败：密码错误 - {}", username);
                return ResponseResult.<AppLoginResponse>fail("用户名或密码错误");
            }

            // 5. 生成token
            String token = generateSecureToken();

            // 6. 保存token到数据库
            AuthToken authToken = new AuthToken();
            authToken.setUserId(user.getId());
            authToken.setToken(token);
            authToken.setDeviceInfo(request.getDeviceInfo());
            authTokenRepository.save(authToken);

            // 7. 构建响应
            AppLoginResponse response = AppLoginResponse.builder()
                    .token(token)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .displayName(user.getDisplayName())
                    .build();

            log.info("用户登录成功 - userId: {}, username: {}, deviceInfo: {}", 
                    user.getId(), username, request.getDeviceInfo());

            return ResponseResult.success(response);

        } catch (Exception e) {
            log.error("登录接口异常", e);
            return ResponseResult.<AppLoginResponse>fail("登录失败，请稍后重试");
        }
    }

    /**
     * App登出接口
     * @param authHeader Authorization header (Bearer token)
     * @return 登出结果
     */
    @PostMapping("/app-logout")
    @Transactional
    public ResponseResult<Void> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // 1. 从请求头获取token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseResult.fail(null, "未提供有效的认证信息");
            }

            String token = authHeader.substring(7); // 移除 "Bearer " 前缀

            // 2. 查询token
            Optional<AuthToken> authTokenOpt = authTokenRepository.findByToken(token);
            if (!authTokenOpt.isPresent()) {
                log.warn("登出失败：token不存在");
                // 即使token不存在也返回成功，因为目标已达成（用户已登出）
                return ResponseResult.success();
            }

            AuthToken authToken = authTokenOpt.get();

            // 3. 删除token
            authTokenRepository.deleteByToken(token);

            log.info("用户登出成功 - userId: {}", authToken.getUserId());

            return ResponseResult.success();

        } catch (Exception e) {
            log.error("登出接口异常", e);
            return ResponseResult.fail(null, "登出失败，请稍后重试");
        }
    }

    /**
     * 生成安全的token
     * 使用128位随机数，Base64编码
     * @return 生成的token字符串
     */
    private String generateSecureToken() {
        byte[] randomBytes = new byte[32]; // 32 bytes = 256 bits
        SECURE_RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

