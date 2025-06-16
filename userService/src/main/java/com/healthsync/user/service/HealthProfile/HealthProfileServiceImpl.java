package com.healthsync.user.service.HealthProfile;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import com.healthsync.user.domain.HealthCheck.HealthNormalRange;
import com.healthsync.user.repository.entity.HealthCheckupRawEntity;
import com.healthsync.user.repository.entity.HealthNormalRangeEntity;
import com.healthsync.user.repository.jpa.HealthCheckupRawRepository;
import com.healthsync.user.repository.jpa.HealthNormalRangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HealthProfileServiceImpl implements HealthProfileService {

    private static final Logger logger = LoggerFactory.getLogger(HealthProfileServiceImpl.class);

    private final HealthCheckupRawRepository healthCheckupRawRepository;
    private final HealthNormalRangeRepository healthNormalRangeRepository;

    public HealthProfileServiceImpl(HealthCheckupRawRepository healthCheckupRawRepository,
                                    HealthNormalRangeRepository healthNormalRangeRepository) {
        this.healthCheckupRawRepository = healthCheckupRawRepository;
        this.healthNormalRangeRepository = healthNormalRangeRepository;
    }

    @Override
    public Optional<HealthCheckupRaw> getMostRecentHealthCheckup(String name, LocalDate birthDate) {
        logger.info("최근 건강검진 데이터 조회 - 이름: {}, 생년월일: {}", name, birthDate);

        Optional<HealthCheckupRawEntity> entity = healthCheckupRawRepository
                .findMostRecentByNameAndBirthDate(name, birthDate);

        if (entity.isPresent()) {
            logger.info("건강검진 데이터 발견 - 검진년도: {}, Raw ID: {}, 성별 코드: {}",
                    entity.get().getReferenceYear(), entity.get().getRawId(), entity.get().getGenderCode());
            return Optional.of(entity.get().toDomain());
        } else {
            logger.warn("해당 사용자의 건강검진 데이터를 찾을 수 없음 - 이름: {}, 생년월일: {}", name, birthDate);
            return Optional.empty();
        }
    }

    @Override
    public List<HealthCheckupRaw> getHealthCheckupHistory(String name, LocalDate birthDate) {
        logger.info("건강검진 이력 조회 - 이름: {}, 생년월일: {}", name, birthDate);

        List<HealthCheckupRawEntity> entities = healthCheckupRawRepository
                .findAllByNameAndBirthDate(name, birthDate);

        logger.info("건강검진 이력 {}건 발견", entities.size());

        return entities.stream()
                .map(HealthCheckupRawEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<HealthNormalRange> getAllHealthNormalRanges() {
        logger.info("모든 건강 정상 범위 데이터 조회");

        List<HealthNormalRangeEntity> entities = healthNormalRangeRepository.findAll();

        logger.info("건강 정상 범위 데이터 {}건 조회됨", entities.size());

        return entities.stream()
                .map(HealthNormalRangeEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<HealthNormalRange> getHealthNormalRangesByGender(Integer genderCode) {
        logger.info("성별별 건강 정상 범위 데이터 조회 - 성별 코드: {}", genderCode);

        List<HealthNormalRangeEntity> entities = healthNormalRangeRepository.findByGenderCode(genderCode);

        logger.info("성별별 건강 정상 범위 데이터 {}건 조회됨", entities.size());

        return entities.stream()
                .map(HealthNormalRangeEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HealthNormalRange> getHealthNormalRangeByItemCode(String healthItemCode, Integer genderCode) {
        logger.info("특정 건강 항목 정상 범위 조회 - 항목 코드: {}, 성별 코드: {}", healthItemCode, genderCode);

        Optional<HealthNormalRangeEntity> entity = healthNormalRangeRepository
                .findByHealthItemCodeAndGenderCode(healthItemCode, genderCode);

        if (entity.isPresent()) {
            logger.info("건강 항목 정상 범위 발견 - 항목명: {}", entity.get().getHealthItemName());
            return Optional.of(entity.get().toDomain());
        } else {
            logger.warn("해당 건강 항목의 정상 범위를 찾을 수 없음 - 항목 코드: {}, 성별 코드: {}",
                    healthItemCode, genderCode);
            return Optional.empty();
        }
    }

    @Override
    public List<HealthNormalRange> getRelevantHealthNormalRanges(Integer genderCode) {
        logger.info("성별에 맞는 건강 정상 범위 데이터 조회 - 성별 코드: {}", genderCode);

        List<HealthNormalRangeEntity> entities = healthNormalRangeRepository
                .findRelevantByGenderCode(genderCode);

        logger.info("관련 건강 정상 범위 데이터 {}건 조회됨", entities.size());

        return entities.stream()
                .map(HealthNormalRangeEntity::toDomain)
                .collect(Collectors.toList());
    }
}