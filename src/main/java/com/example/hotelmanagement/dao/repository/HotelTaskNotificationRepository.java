package com.example.hotelmanagement.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hotelmanagement.dao.entity.HotelTaskNotification;

@Repository
public interface HotelTaskNotificationRepository extends JpaRepository<HotelTaskNotification, Long> {

    /**
     * 根据用户ID查找通知
     */
    List<HotelTaskNotification> findByUserIdOrderByCreateTimeDesc(Long userId);

    /**
     * 根据任务ID查找通知
     */
    List<HotelTaskNotification> findByTaskId(Long taskId);

    /**
     * 根据用户ID统计未读通知数量
     */
    long countByUserIdAndAlreadyRead(Long userId, Short alreadyRead);

    /**
     * 批量标记用户的通知为已读
     */
    @Modifying
    @Query("UPDATE HotelTaskNotification n SET n.alreadyRead = 1 WHERE n.userId = :userId AND n.alreadyRead = 0")
    int markAllAsReadByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和通知ID列表批量标记为已读
     */
    @Modifying
    @Query("UPDATE HotelTaskNotification n SET n.alreadyRead = 1 WHERE n.userId = :userId AND n.id IN :notificationIds")
    int markAsReadByUserIdAndIds(@Param("userId") Long userId, @Param("notificationIds") List<Long> notificationIds);

    /**
     * 删除指定任务的所有通知
     */
    void deleteByTaskId(Long taskId);

    /**
     * 分页查询用户的通知（游标分页）
     */
    @Query(value = "SELECT * FROM hotel_task_notification n WHERE n.user_id = :userId " +
            "AND (:lastNotificationId IS NULL OR n.id < :lastNotificationId) " +
            "ORDER BY n.id DESC " +
            "LIMIT CAST(:limit AS INTEGER)",
            nativeQuery = true)
    List<HotelTaskNotification> findByUserIdWithCursorPagination(
            @Param("userId") Long userId,
            @Param("lastNotificationId") Long lastNotificationId,
            @Param("limit") Integer limit);
}
