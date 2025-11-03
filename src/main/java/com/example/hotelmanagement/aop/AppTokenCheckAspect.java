package com.example.hotelmanagement.aop;

import com.example.hotelmanagement.dao.entity.AuthToken;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.repository.AuthTokenRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.model.response.ResponseResult;
import com.example.hotelmanagement.util.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * AOP切面：校验App端Token
 * 从 Authorization header 中获取 Bearer token，验证其有效性，并将用户信息存入 AppContext
 */
@Slf4j
@Aspect
@Component
public class AppTokenCheckAspect {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private HotelUserRepository userRepository;

    @Around("@within(com.example.hotelmanagement.aop.annotation.RequireAppToken) || @annotation(com.example.hotelmanagement.aop.annotation.RequireAppToken)")
    public Object checkAppToken(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String authHeader = request.getHeader("Authorization");

            // 1. 检查 Authorization header 是否存在
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("App接口调用失败：未提供有效的认证信息");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseResult.fail(null, "未提供有效的认证信息"));
            }

            // 2. 提取 token
            String token = authHeader.substring(7); // 移除 "Bearer " 前缀

            // 3. 查询 token
            Optional<AuthToken> authTokenOpt = authTokenRepository.findByToken(token);
            if (!authTokenOpt.isPresent()) {
                log.warn("App接口调用失败：Token无效或已过期");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseResult.fail(null, "Token无效或已过期"));
            }

            AuthToken authToken = authTokenOpt.get();
            Long userId = authToken.getUserId();

            // 4. 查询用户信息
            Optional<HotelUser> userOpt = userRepository.findById(userId);
            if (!userOpt.isPresent()) {
                log.warn("App接口调用失败：用户不存在 - userId: {}", userId);
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseResult.fail(null, "用户不存在"));
            }

            HotelUser user = userOpt.get();

            // 5. 检查用户状态
            if (user.getActive() == null || user.getActive() != 1) {
                log.warn("App接口调用失败：用户已被禁用 - userId: {}", userId);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(ResponseResult.fail(null, "该账号已被禁用"));
            }

            // 6. 将用户信息存入 AppContext
            AppContext.setUserId(userId);
            AppContext.setUsername(user.getUsername());
            AppContext.setToken(token);

            log.debug("App接口校验通过 - userId: {}, username: {}", userId, user.getUsername());

            // 7. 继续执行目标方法
            return joinPoint.proceed();

        } finally {
            // 8. 清理 ThreadLocal，防止内存泄漏
            AppContext.clear();
        }
    }
}

