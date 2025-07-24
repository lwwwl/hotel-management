package com.example.hotelmanagement.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * 房间表实体
 */
@Data
@Entity
@Table(name = "hotel_room")
public class HotelRoom {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 房间名称
     */
    @Column(name = "name", length = 128)
    private String name;

    /**
     * 是否有效 0-无效 1-有效
     */
    @Column(name = "active")
    private Short active;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;
} 