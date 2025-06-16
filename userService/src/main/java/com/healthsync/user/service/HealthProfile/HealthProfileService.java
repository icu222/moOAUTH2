package com.healthsync.user.service.HealthProfile;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import com.healthsync.user.domain.HealthCheck.HealthNormalRange;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HealthProfileService {

    /**
     * 이름과 생년월일로 최근 건강검진 데이터 조회
     */
    Optional<HealthCheckupRaw> getMostRecentHealthCheckup(String name, LocalDate birthDate);

    /**
     * 이름과 생년월일로 모든 건강검진 이력 조회
     */
    List<HealthCheckupRaw> getHealthCheckupHistory(String name, LocalDate birthDate);

    /**
     * 모든 건강 정상 범위 데이터 조회
     */
    List<HealthNormalRange> getAllHealthNormalRanges();

    /**
     * 성별 코드에 따른 건강 정상 범위 데이터 조회
     */
    List<HealthNormalRange> getHealthNormalRangesByGender(Integer genderCode);

    /**
     * 특정 건강 항목의 정상 범위 조회
     */
    Optional<HealthNormalRange> getHealthNormalRangeByItemCode(String healthItemCode, Integer genderCode);
}