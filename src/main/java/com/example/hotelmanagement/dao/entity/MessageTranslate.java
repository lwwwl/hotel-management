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
 * 消息翻译结果表实体
 */
@Data
@Entity
@Table(name = "message_translate")
public class MessageTranslate {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会话id
     */
    @Column(name = "conversation_id")
    private Long conversationId;

    /**
     * 消息id
     */
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 语言
     */
    @Column(name = "language", length = 32)
    private String language;

    /**
     * 翻译结果
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

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


