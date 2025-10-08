package com.example.hotelmanagement.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hotelmanagement.dao.entity.HotelMenu;

@Repository
public interface HotelMenuRepository extends JpaRepository<HotelMenu, Long> {
    
    /**
     * Find all top-level menus
     */
    @Query(value = "SELECT m FROM HotelMenu m WHERE m.parentId = 0 AND m.active = true ORDER BY m.sortOrder ASC")
    List<HotelMenu> findAllActiveTopLevelMenus();

    /**
     * Find menus by multiple parent IDs
     */
    List<HotelMenu> findByParentIdInOrderBySortOrderAsc(List<Long> parentIds);
} 