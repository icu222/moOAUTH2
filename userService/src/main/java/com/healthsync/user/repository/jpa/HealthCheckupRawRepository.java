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

    // 이름과 생년월일로 최근 건강검진 데이터 1개만 조회
    @Query("SELECT h FROM HealthCheckupRawEntity h " +
            "WHERE h.name = :name AND h.birthDate = :birthDate " +
            "ORDER BY h.referenceYear DESC, h.createdAt DESC")
    List<HealthCheckupRawEntity> findFirstByNameAndBirthDateOrderByReferenceYearDesc(
            @Param("name") String name,
            @Param("birthDate") LocalDate birthDate);

    // 실제로 사용할 메서드 (첫 번째 결과만 반환)
    default Optional<HealthCheckupRawEntity> findMostRecentByNameAndBirthDate(String name, LocalDate birthDate) {
        List<HealthCheckupRawEntity> results = findFirstByNameAndBirthDateOrderByReferenceYearDesc(name, birthDate);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

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