package com.healthsync.user.repository.jpa;

import com.healthsync.user.repository.entity.HealthNormalRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthNormalRangeRepository extends JpaRepository<HealthNormalRangeEntity, Integer> {

    // 모든 정상 범위 데이터 조회
    List<HealthNormalRangeEntity> findAll();

    // 특정 건강 항목 코드로 조회
    Optional<HealthNormalRangeEntity> findByHealthItemCode(String healthItemCode);

    // 성별 코드로 필터링하여 조회
    List<HealthNormalRangeEntity> findByGenderCode(Integer genderCode);

    // 특정 건강 항목 코드와 성별 코드로 조회
    @Query("SELECT h FROM HealthNormalRangeEntity h " +
            "WHERE h.healthItemCode = :healthItemCode " +
            "AND (h.genderCode = :genderCode OR h.genderCode IS NULL)")
    Optional<HealthNormalRangeEntity> findByHealthItemCodeAndGenderCode(
            @Param("healthItemCode") String healthItemCode,
            @Param("genderCode") Integer genderCode);

    // 건강 항목명으로 조회
    List<HealthNormalRangeEntity> findByHealthItemName(String healthItemName);

    // 성별에 맞는 정상 범위 조회 (해당 성별 + 범용(null))
    @Query("SELECT h FROM HealthNormalRangeEntity h " +
            "WHERE h.genderCode = :genderCode OR h.genderCode IS NULL " +
            "ORDER BY h.healthItemCode, h.genderCode DESC")
    List<HealthNormalRangeEntity> findRelevantByGenderCode(@Param("genderCode") Integer genderCode);
}