package com.example.hotelmanagement.util;

/**
 * App context utility for storing app user information in ThreadLocal
 */
public class AppContext {
    
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    
    /**
     * Set user ID
     */
    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }
    
    /**
     * Get user ID
     */
    public static Long getUserId() {
        return USER_ID.get();
    }
    
    /**
     * Set username
     */
    public static void setUsername(String username) {
        USERNAME.set(username);
    }
    
    /**
     * Get username
     */
    public static String getUsername() {
        return USERNAME.get();
    }
    
    /**
     * Set token
     */
    public static void setToken(String token) {
        TOKEN.set(token);
    }
    
    /**
     * Get token
     */
    public static String getToken() {
        return TOKEN.get();
    }
    
    /**
     * Clear all context data
     */
    public static void clear() {
        USER_ID.remove();
        USERNAME.remove();
        TOKEN.remove();
    }
}

