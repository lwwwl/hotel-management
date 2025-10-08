package com.example.hotelmanagement.util;

import java.security.SecureRandom;

public class RandomEmailGenerator {

    // 定义用于生成邮箱用户名的字符集（小写字母 + 数字）
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    
    // 定义固定的邮箱后缀
    private static final String DOMAIN = "@hotel.com";

    // 定义用户名的固定长度为12位
    private static final int USERNAME_LENGTH = 12;

    // 使用 SecureRandom 以获得更安全的随机性
    private static final SecureRandom random = new SecureRandom();

    /**
     * 生成一个固定12位用户名的随机邮箱地址
     * @return 随机邮箱字符串，例如 "a1b2c3d4e5f6@hotel.com"
     */
    public static String generate() {
        // 1. 使用 StringBuilder 高效构建字符串，并指定固定容量
        StringBuilder builder = new StringBuilder(USERNAME_LENGTH);

        // 2. 循环12次，生成每个随机字符
        for (int i = 0; i < USERNAME_LENGTH; i++) {
            // 从 ALLOWED_CHARACTERS 中随机取一个字符的索引
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            // 将该字符追加到 builder
            builder.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        // 3. 组合用户名和固定的域名
        return builder.toString() + DOMAIN;
    }

    /**
     * main 方法，用于演示和测试
     */
    public static void main(String[] args) {
        System.out.println("正在生成5个用户名长度为12位的随机邮箱地址...");
        
        for (int i = 0; i < 5; i++) {
            String randomEmail = generate();
            System.out.println("邮箱 " + (i + 1) + ": " + randomEmail);
        }
    }
}