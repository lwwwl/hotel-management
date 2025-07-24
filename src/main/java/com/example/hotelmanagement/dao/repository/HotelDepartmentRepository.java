package com.example.hotelmanagement.dao.repository;

import com.example.hoteltask.dao.entity.HotelDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelDepartmentRepository extends JpaRepository<HotelDepartment, Long> {

    /**
     * Find departments by parent ID
     */
    List<HotelDepartment> findByParentIdOrderByIdAsc(Long parentId);
    
    /**
     * Find departments by leader user ID
     */
    List<HotelDepartment> findByLeaderUserId(Long leaderUserId);
    
    /**
     * Find all top-level departments
     */
    @Query(value = "SELECT d FROM HotelDepartment d WHERE d.parentId = 0 ORDER BY d.id ASC")
    List<HotelDepartment> findAllTopLevelDepartments();
    
    /**
     * Search departments by name
     */
    @Query(value = "SELECT d FROM HotelDepartment d WHERE d.name LIKE %:keyword%")
    List<HotelDepartment> searchByName(@Param("keyword") String keyword);

    /**
     * 根据id列表查找部门
     */
    List<HotelDepartment> findByIdIn(List<Long> ids);
} 