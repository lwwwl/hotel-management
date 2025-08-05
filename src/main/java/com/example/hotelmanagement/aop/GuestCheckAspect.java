package com.example.hotelmanagement.aop;

import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.util.GuestContext;
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
public class GuestCheckAspect {
    @Around("@within(com.example.hotelmanagement.aop.annotation.RequireGuestId) || @annotation(com.example.hotelmanagement.aop.annotation.RequireGuestId)")
    public Object checkGuestIdHeader(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String guestIdStr = request.getHeader("X-Guest-Id");

            if (guestIdStr == null || guestIdStr.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),
                                "Missing required X-Guest-Id header",
                                "Unauthorized"));
            }
            try {
                Long guestId = Long.parseLong(guestIdStr);
                GuestContext.setGuestId(guestId);
            } catch (NumberFormatException e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(),
                                "Invalid X-Guest-Id format",
                                "Bad Request"));
            }
            return joinPoint.proceed();
        } finally {
            GuestContext.clear();
        }
    }
}
