package com.healthsync.user.dto;

import com.healthsync.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserProfileResponse {
    private Long memberSerialNumber;
    private String googleId;
    private String name;
    private LocalDate birthDate;
    private String occupation;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public UserProfileResponse() {}

    public UserProfileResponse(User user) {
        this.memberSerialNumber = user.getMemberSerialNumber();
        this.googleId = user.getGoogleId();
        this.name = user.getName();
        this.birthDate = user.getBirthDate();
        this.occupation = user.getOccupation();
        this.createdAt = user.getCreatedAt();
        this.lastLoginAt = user.getLastLoginAt();
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

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}