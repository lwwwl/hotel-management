package com.example.hotelmanagement.dao.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hotel_events")
public class HotelEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", length = 32)
    private String eventType;

    @Column(name = "event_sub_type", length = 32)
    private String eventSubType;

    @Column(name = "event_content", columnDefinition = "TEXT")
    private String eventContent;

    @Column(name = "event_level", length = 32)
    private String eventLevel;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
