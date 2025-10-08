package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRoleRepository extends JpaRepository<HotelRole, Long> {

    /**
     * Find role by name
     */
    Optional<HotelRole> findByName(String name);
    
    /**
     * Search roles by name or description
     */
    @Query(value = "SELECT r FROM HotelRole r WHERE r.name LIKE %:keyword% OR r.description LIKE %:keyword% ORDER BY r.id ASC")
    List<HotelRole> searchByNameOrDescription(@Param("keyword") String keyword);
    
    /**
     * Find roles with member count greater than specified value
     */
    List<HotelRole> findByMemberCountGreaterThan(Integer memberCount);
} 