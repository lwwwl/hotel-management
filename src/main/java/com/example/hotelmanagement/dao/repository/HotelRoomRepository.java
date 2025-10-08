package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {

    /**
     * 根据房间名称查找
     */
    Optional<HotelRoom> findByName(String name);
    
    /**
     * 查找所有有效的房间
     */
    List<HotelRoom> findByActiveOrderByNameAscIdAsc(Short active);
    
    /**
     * 根据名称模糊搜索房间
     */
    @Query(value = "SELECT r FROM HotelRoom r WHERE r.name LIKE %:keyword% ORDER BY r.id ASC")
    List<HotelRoom> searchByName(@Param("keyword") String keyword);
    
    /**
     * 查找所有有效房间数量
     */
    long countByActive(Short active);

    /**
     * 根据id列表查找房间
     */
    List<HotelRoom> findByIdIn(List<Long> ids);
} 