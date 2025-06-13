package com.healthsync.user.repository.jpa;

import com.healthsync.user.repository.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByMemberSerialNumber(Long memberSerialNumber);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.memberSerialNumber = :memberSerialNumber")
    void deleteByMemberSerialNumber(@Param("memberSerialNumber") Long memberSerialNumber);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiryDate < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
}
