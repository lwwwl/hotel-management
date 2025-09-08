package com.example.hotelmanagement.dao.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 快捷菜单表实体
 */
@Data
@Entity
@Table(name = "quick_menu")
public class QuickMenu {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图标
     */
    @Column(name = "icon", length = 128)
    private String icon;

    /**
     * 存储title、body多种语言的信息
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * 排序序号，越小越靠前
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

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


