package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelUserRepository extends JpaRepository<HotelUser, Long> {

    /**
     * Find user by username
     */
    Optional<HotelUser> findByUsername(String username);
    
    /**
     * Find user by email
     */
    Optional<HotelUser> findByEmail(String email);
    
    /**
     * Find user by phone
     */
    Optional<HotelUser> findByPhone(String phone);
    
    /**
     * Find active users
     */
    List<HotelUser> findByActiveOrderByIdDesc(Short active);
    
    /**
     * Find users by employee number
     */
    Optional<HotelUser> findByEmployeeNumber(String employeeNumber);
    
    /**
     * Search users by display name
     */
    @Query(value = "SELECT u FROM HotelUser u WHERE u.displayName LIKE %:keyword% AND u.active = 1")
    List<HotelUser> searchByDisplayName(@Param("keyword") String keyword);
    
    /**
     * 综合搜索用户（使用游标分页）
     * 支持按用户名、工号、姓名进行模糊搜索
     * 支持按部门ID和锁定状态过滤
     * 按创建时间正序排序，然后按ID正序排序
     * 
     * @param keyword 搜索关键词，用于匹配用户名、工号、姓名
     * @param deptId 部门ID，可为null
     * @param active 锁定状态，可为null
     * @param lastCreateTime 上一页最后一条记录的创建时间，首次查询为null
     * @param lastUserId 上一页最后一条记录的用户ID，首次查询为null
     * @param limit 每页记录数
     * @return 符合条件的用户列表
     */
    @Query(value = "SELECT DISTINCT u.* FROM hotel_users u " +
           "LEFT JOIN hotel_user_department ud ON u.id = ud.user_id " +
           "WHERE (:keyword IS NULL OR u.username LIKE %:keyword% OR u.employee_number LIKE %:keyword% OR u.display_name LIKE %:keyword%) " +
           "AND (:deptId IS NULL OR ud.dept_id = :deptId) " +
           "AND (:active IS NULL OR u.active = :active) " +
           "AND (:lastCreateTime IS NULL OR u.create_time > :lastCreateTime " +
           "     OR (u.create_time = :lastCreateTime AND u.id > :lastUserId)) " +
           "ORDER BY u.create_time ASC, u.id ASC " +
           "LIMIT :limit", 
           nativeQuery = true)
    List<HotelUser> searchUsers(
            @Param("keyword") String keyword, 
            @Param("deptId") Long deptId, 
            @Param("active") Short active,
            @Param("lastCreateTime") Timestamp lastCreateTime,
            @Param("lastUserId") Long lastUserId,
            @Param("limit") int limit);
} 