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
} 