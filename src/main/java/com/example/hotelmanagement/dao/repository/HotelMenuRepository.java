package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelMenuRepository extends JpaRepository<HotelMenu, Long> {

    /**
     * Find menus by parent ID
     */
    List<HotelMenu> findByParentIdOrderBySortOrderAsc(Long parentId);
    
    /**
     * Find menus by type
     */
    List<HotelMenu> findByType(Short type);
    
    /**
     * Find visible menus
     */
    List<HotelMenu> findByVisibleOrderBySortOrderAsc(Boolean visible);
    
    /**
     * Find all top-level menus
     */
    @Query(value = "SELECT m FROM HotelMenu m WHERE m.parentId = 0 AND m.visible = true ORDER BY m.sortOrder ASC")
    List<HotelMenu> findAllVisibleTopLevelMenus();
    
    /**
     * Find menus by path
     */
    List<HotelMenu> findByPathLike(String path);
} 