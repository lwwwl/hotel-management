package com.example.hotelmanagement.aop;

import com.example.hotelmanagement.model.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class UserIdCheckAspect {

    @Around("@within(com.example.hotelmanagement.aop.annotation.RequireUserId) || @annotation(com.example.hotelmanagement.aop.annotation.RequireUserId)")
    public Object checkUserIdHeader(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userId = request.getHeader("X-User-Id");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),
                                          "Missing required userId header", 
                                          "Unauthorized"));
        }
        
        return joinPoint.proceed();
    }
} 