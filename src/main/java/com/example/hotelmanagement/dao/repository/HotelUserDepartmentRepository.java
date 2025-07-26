package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelUserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelUserDepartmentRepository extends JpaRepository<HotelUserDepartment, Long> {
    List<HotelUserDepartment> findByDeptId(Long deptId);

    List<HotelUserDepartment> findByUserId(Long userId);

    void deleteByDeptId(Long deptId);

    void deleteByDeptIdAndUserIdIn(Long deptId, List<Long> userIds);

    @Query("SELECT DISTINCT hud.deptId FROM HotelUserDepartment hud WHERE hud.userId = :userId")
    List<Long> findDeptIdsByUserId(Long userId);
} 