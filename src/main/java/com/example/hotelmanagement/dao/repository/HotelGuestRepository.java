package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelGuestRepository extends JpaRepository<HotelGuest, Long> {

    /**
     * Find guest by chatwoot ID
     */
    Optional<HotelGuest> findByChatwootId(String chatwootId);
    
    /**
     * Find guests by room number
     */
    List<HotelGuest> findByRoomNumber(String roomNumber);
    
    /**
     * Find guests by verification status
     */
    List<HotelGuest> findByVerifyOrderByCheckInTimeDesc(Short verify);
    
    /**
     * Find guests by check-in time range
     */
    @Query(value = "SELECT g FROM HotelGuest g WHERE g.checkInTime >= :startTime AND g.checkInTime <= :endTime")
    List<HotelGuest> findByCheckInTimeRange(
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);
    
    /**
     * Search guests by name
     */
    @Query(value = "SELECT g FROM HotelGuest g WHERE g.guestName LIKE %:keyword%")
    List<HotelGuest> searchByGuestName(@Param("keyword") String keyword);
    
    /**
     * Find guests by phone suffix
     */
    Optional<HotelGuest> findByPhoneSuffix(String phoneSuffix);

    /**
     * 根据id列表查找客人
     */
    List<HotelGuest> findByIdIn(List<Long> ids);

    /**
     * 根据guestId更新chatwootContactId和chatwootResourceId
     */
    @Modifying
    @Query(value = "UPDATE hotel_guests SET chatwoot_contact_id = :chatwootContactId, chatwoot_resource_id = :chatwootResourceId WHERE id = :guestId", nativeQuery = true)
    void updateChatwootContactIdAndResourceId(@Param("guestId") Long guestId, @Param("chatwootContactId") Long chatwootContactId, @Param("chatwootResourceId") String chatwootResourceId);
} 