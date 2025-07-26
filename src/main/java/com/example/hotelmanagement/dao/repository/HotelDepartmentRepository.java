package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelDepartmentRepository extends JpaRepository<HotelDepartment, Long> {
} 