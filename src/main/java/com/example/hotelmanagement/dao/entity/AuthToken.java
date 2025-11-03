package com.example.hotelmanagement.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * App登录Token表实体
 */
@Data
@Entity
@Table(name = "auth_tokens")
public class AuthToken {

    /**
     * Token ID（主键，自增）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 关联的用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 登录Token（唯一）
     */
    @Column(name = "token", nullable = false, unique = true, columnDefinition = "TEXT")
    private String token;

    /**
     * 设备信息（可选）
     */
    @Column(name = "device_info", length = 255)
    private String deviceInfo;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}

