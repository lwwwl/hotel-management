package com.example.hotelmanagement.dao.repository;

import com.example.hotelmanagement.dao.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    /**
     * 根据token查找认证记录
     */
    Optional<AuthToken> findByToken(String token);

    /**
     * 根据userId查找所有token记录
     */
    @Query("SELECT at FROM AuthToken at WHERE at.userId = :userId")
    java.util.List<AuthToken> findAllByUserId(@Param("userId") Long userId);

    /**
     * 根据token删除认证记录
     */
    @Modifying
    @Query("DELETE FROM AuthToken at WHERE at.token = :token")
    int deleteByToken(@Param("token") String token);

    /**
     * 根据userId删除所有认证记录（登出所有设备）
     */
    @Modifying
    @Query("DELETE FROM AuthToken at WHERE at.userId = :userId")
    int deleteAllByUserId(@Param("userId") Long userId);
}

