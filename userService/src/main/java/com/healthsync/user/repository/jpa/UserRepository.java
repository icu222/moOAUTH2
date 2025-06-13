package com.healthsync.user.repository.jpa;

import com.healthsync.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 기본 조회 메서드
    Optional<UserEntity> findByGoogleId(String googleId);
    boolean existsByGoogleId(String googleId);

    // 이름으로 검색
    List<UserEntity> findByNameContaining(String name);

    // 직업으로 검색
    List<UserEntity> findByOccupation(String occupation);
    List<UserEntity> findByOccupationContaining(String occupation);

    // 생년월일 범위 검색
    List<UserEntity> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    // 가입일 범위 검색
    List<UserEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 최근 로그인한 사용자들
    @Query("SELECT u FROM UserEntity u WHERE u.lastLoginAt >= :since ORDER BY u.lastLoginAt DESC")
    List<UserEntity> findRecentlyLoggedInUsers(@Param("since") LocalDateTime since);

    // 특정 기간 동안 로그인하지 않은 사용자들
    @Query("SELECT u FROM UserEntity u WHERE u.lastLoginAt < :before OR u.lastLoginAt IS NULL")
    List<UserEntity> findInactiveUsers(@Param("before") LocalDateTime before);

    // 로그인 시간 업데이트
    @Modifying
    @Query("UPDATE UserEntity u SET u.lastLoginAt = :lastLoginAt, u.updatedAt = :updatedAt WHERE u.memberSerialNumber = :memberSerialNumber")
    void updateLastLoginAt(@Param("memberSerialNumber") Long memberSerialNumber,
                           @Param("lastLoginAt") LocalDateTime lastLoginAt,
                           @Param("updatedAt") LocalDateTime updatedAt);

    // 사용자 정보 부분 업데이트
    @Modifying
    @Query("UPDATE UserEntity u SET u.name = :name, u.updatedAt = :updatedAt WHERE u.memberSerialNumber = :memberSerialNumber")
    void updateUserName(@Param("memberSerialNumber") Long memberSerialNumber,
                        @Param("name") String name,
                        @Param("updatedAt") LocalDateTime updatedAt);

    @Modifying
    @Query("UPDATE UserEntity u SET u.birthDate = :birthDate, u.updatedAt = :updatedAt WHERE u.memberSerialNumber = :memberSerialNumber")
    void updateUserBirthDate(@Param("memberSerialNumber") Long memberSerialNumber,
                             @Param("birthDate") LocalDate birthDate,
                             @Param("updatedAt") LocalDateTime updatedAt);

    @Modifying
    @Query("UPDATE UserEntity u SET u.occupation = :occupation, u.updatedAt = :updatedAt WHERE u.memberSerialNumber = :memberSerialNumber")
    void updateUserOccupation(@Param("memberSerialNumber") Long memberSerialNumber,
                              @Param("occupation") String occupation,
                              @Param("updatedAt") LocalDateTime updatedAt);

    // 통계 관련 쿼리
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.createdAt >= :startDate")
    long countNewUsersFrom(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.lastLoginAt >= :startDate")
    long countActiveUsersFrom(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT u.occupation, COUNT(u) FROM UserEntity u WHERE u.occupation IS NOT NULL GROUP BY u.occupation")
    List<Object[]> countUsersByOccupation();

    // 생년월일이 설정되지 않은 사용자들 (임시값 사용자들)
    @Query("SELECT u FROM UserEntity u WHERE u.birthDate = :defaultDate")
    List<UserEntity> findUsersWithDefaultBirthDate(@Param("defaultDate") LocalDate defaultDate);

    // 프로필이 완성되지 않은 사용자들
    @Query("SELECT u FROM UserEntity u WHERE u.birthDate = :defaultDate OR u.occupation IS NULL")
    List<UserEntity> findIncompleteProfiles(@Param("defaultDate") LocalDate defaultDate);
}