package com.example.hotelmanagement.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hotelmanagement.dao.entity.HotelUserDepartment;

public interface HotelUserDepartmentRepository extends JpaRepository<HotelUserDepartment, Long> {
    List<HotelUserDepartment> findByDeptId(Long deptId);

    List<HotelUserDepartment> findByUserId(Long userId);

    void deleteByDeptId(Long deptId);

    void deleteByDeptIdAndUserIdIn(Long deptId, List<Long> userIds);

    @Query("SELECT DISTINCT hud.deptId FROM HotelUserDepartment hud WHERE hud.userId = :userId")
    List<Long> findDeptIdsByUserId(Long userId);

    List<HotelUserDepartment> findByDeptIdAndUserId(Long deptId, Long userId);
} 