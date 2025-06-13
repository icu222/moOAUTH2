package com.healthsync.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private Long memberSerialNumber; // ID는 DB에서 자동 생성
    private String googleId;
    private String name;
    private LocalDate birthDate;
    private String occupation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 신규 사용자 생성용 생성자 (ID 없음)
    public User(String googleId, String name, LocalDate birthDate, String occupation) {
        this(); // 기본 생성자 호출
        this.googleId = googleId;
        this.name = name;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.lastLoginAt = LocalDateTime.now();
        // memberSerialNumber는 설정하지 않음 - DB에서 자동 생성
    }

    // 로그인 시간 업데이트
    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getMemberSerialNumber() { return memberSerialNumber; }
    public void setMemberSerialNumber(Long memberSerialNumber) { this.memberSerialNumber = memberSerialNumber; }

    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.updatedAt = LocalDateTime.now();
    }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}