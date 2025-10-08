package com.example.hotelmanagement.util;

import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码处理工具类，提供密码哈希和校验功能。
 * <p>
 * 使用 Spring Security Crypto 实现，生成与 OpenLDAP 兼容的 {SSHA} 格式哈希。
 * 该类为 final 且构造函数为 private，不允许实例化。
 */
public final class PasswordUtil {

    // 直接使用 LdapShaPasswordEncoder，它能生成 OpenLDAP 兼容的 {SSHA} 格式哈希。
    private static final PasswordEncoder PASSWORD_ENCODER = new LdapShaPasswordEncoder();

    /**
     * 工具类的构造函数应为 private，防止外部通过 new PasswordUtil() 创建实例。
     */
    private PasswordUtil() {
        // 防止实例化
    }

    /**
     * 将明文密码哈希成带 {bcrypt} 前缀的格式。
     *
     * @param rawPassword 用户输入的明文密码
     * @return 用于存储到数据库和 LDAP 的哈希字符串
     */
    public static String hashPassword(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    /**
     * 校验明文密码和已编码的哈希是否匹配。
     *
     * @param rawPassword     用户输入的明文密码
     * @param encodedPassword 从数据库中读取的、带前缀的哈希字符串
     * @return 如果匹配则返回 true
     */
    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

    // --- Demo Main 方法 ---
    public static void main(String[] args) {
        String userPassword = "admin123"; // 用户的原始密码

        // 1. 生成哈希 - 直接通过类名调用静态方法
        String hashedPassword = PasswordUtil.hashPassword(userPassword);

        System.out.println("生成的哈希 (存入 PG 和 LDAP): " + hashedPassword);
        // 输出示例: 生成的哈希 (存入 PG 和 LDAP): {SSHA}QicuQAFkVb1bNgcNtxXvbO/Nc25e8TCZXKTorQ==
        // 请注意，这个字符串以 {SSHA} 开头，这是 OpenLDAP 默认支持的格式！

        System.out.println("\n--- 密码校验 ---");

        // 2. 校验正确的密码 - 直接通过类名调用静态方法
        boolean isMatch = PasswordUtil.checkPassword(userPassword, hashedPassword);
        System.out.println("使用 'admin123' 进行校验: " + isMatch); // 输出: true

        // 3. 校验错误的密码 - 直接通过类名调用静态方法
        boolean isWrongMatch = PasswordUtil.checkPassword("wrongpassword", hashedPassword);
        System.out.println("使用 'wrongpassword' 进行校验: " + isWrongMatch); // 输出: false
    }
}