package com.example.hotelmanagement.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelmanagement.dao.entity.QuickMenu;

@Repository
public interface QuickMenuRepository extends JpaRepository<QuickMenu, Long> {
}


