package com.healthsync.user.repository.jpa;

import com.healthsync.user.repository.entity.HealthCheckupRawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthCheckupRawRepository extends JpaRepository<HealthCheckupRawEntity, Long> {

    // 이름과 생년월일로 최근 건강검진 데이터 조회 (가장 최근 연도)
    @Query("SELECT h FROM HealthCheckupRawEntity h " +
            "WHERE h.name = :name AND h.birthDate = :birthDate " +
            "ORDER BY h.referenceYear DESC, h.createdAt DESC")
    List<HealthCheckupRawEntity> findByNameAndBirthDateOrderByReferenceYearDesc(
            @Param("name") String name,
            @Param("birthDate") LocalDate birthDate);

    // 이름과 생년월일로 최근 건강검진 데이터 1개만 조회 (첫 번째 결과만)
    @Query(value = "SELECT * FROM health_service.health_checkup_raw h " +
            "WHERE h.name = :name AND h.birth_date = :birthDate " +
            "ORDER BY h.reference_year DESC, h.created_at DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<HealthCheckupRawEntity> findMostRecentByNameAndBirthDate(
            @Param("name") String name,
            @Param("birthDate") LocalDate birthDate);

    // 특정 연도의 건강검진 데이터 조회
    @Query("SELECT h FROM HealthCheckupRawEntity h " +
            "WHERE h.name = :name AND h.birthDate = :birthDate AND h.referenceYear = :year " +
            "ORDER BY h.createdAt DESC")
    List<HealthCheckupRawEntity> findByNameAndBirthDateAndYear(
            @Param("name") String name,
            @Param("birthDate") LocalDate birthDate,
            @Param("year") Integer year);

    // 이름과 생년월일로 모든 건강검진 이력 조회
    @Query("SELECT h FROM HealthCheckupRawEntity h " +
            "WHERE h.name = :name AND h.birthDate = :birthDate " +
            "ORDER BY h.referenceYear DESC, h.createdAt DESC")
    List<HealthCheckupRawEntity> findAllByNameAndBirthDate(
            @Param("name") String name,
            @Param("birthDate") LocalDate birthDate);
}