package com.healthsync.user.repository.entity;

import com.healthsync.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", schema = "user_service")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_serial_number")
    private Long memberSerialNumber;

    @Column(name = "google_id", length = 255, unique = true, nullable = false)
    private String googleId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "occupation", length = 50)
    private String occupation;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    protected UserEntity() {}

    public UserEntity(String googleId, String name, LocalDate birthDate, String occupation) {
        this.googleId = googleId;
        this.name = name;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.lastLoginAt = LocalDateTime.now();
    }

    public static UserEntity fromDomain(User user) {
        UserEntity entity = new UserEntity();
        // 핵심 수정: 새 엔티티인 경우 ID를 설정하지 않음 (BIGSERIAL이 자동 생성)
        if (user.getMemberSerialNumber() != null) {
            entity.memberSerialNumber = user.getMemberSerialNumber();
        }
        entity.googleId = user.getGoogleId();
        entity.name = user.getName();
        entity.birthDate = user.getBirthDate();
        entity.occupation = user.getOccupation();
        entity.createdAt = user.getCreatedAt();
        entity.updatedAt = user.getUpdatedAt();
        entity.lastLoginAt = user.getLastLoginAt();
        return entity;
    }

    public User toDomain() {
        User user = new User();
        user.setMemberSerialNumber(this.memberSerialNumber);
        user.setGoogleId(this.googleId);
        user.setName(this.name);
        user.setBirthDate(this.birthDate);
        user.setOccupation(this.occupation);
        user.setCreatedAt(this.createdAt);
        user.setUpdatedAt(this.updatedAt);
        user.setLastLoginAt(this.lastLoginAt);
        return user;
    }

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
    public void setName(String name) { this.name = name; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}