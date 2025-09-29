package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.HotelTaskOperateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HotelTaskOperateRecordRepository extends JpaRepository<HotelTaskOperateRecord, Long> {

    List<HotelTaskOperateRecord> findAllByTaskIdOrderByCreateTimeDesc(Long taskId);
} 