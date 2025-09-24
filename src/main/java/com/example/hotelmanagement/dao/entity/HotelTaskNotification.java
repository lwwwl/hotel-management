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
 * 任务通知表实体
 */
@Data
@Entity
@Table(name = "hotel_task_notification")
public class HotelTaskNotification {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 通知标题
     */
    @Column(name = "title", length = 256)
    private String title;

    /**
     * 通知内容
     */
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    /**
     * 任务id
     */
    @Column(name = "task_id")
    private Long taskId;

    /**
     * 通知类型: info-普通通知, alert-提醒通知, success-成功通知
     */
    @Column(name = "notification_type", length = 32)
    private String notificationType;

    /**
     * 是否已读 0-未读 1-已读
     */
    @Column(name = "already_read")
    private Short alreadyRead;

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
