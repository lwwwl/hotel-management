package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelEventRepository extends JpaRepository<HotelEvent, Long>, JpaSpecificationExecutor<HotelEvent> {
}
