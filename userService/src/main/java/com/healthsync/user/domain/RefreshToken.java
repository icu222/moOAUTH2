package com.healthsync.user.domain;

import java.time.LocalDateTime;

public class RefreshToken {
    private Long id;
    private String token;
    private Long memberSerialNumber; // memberId -> memberSerialNumber로 변경
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;

    public RefreshToken() {
        this.createdAt = LocalDateTime.now();
    }

    public RefreshToken(String token, Long memberSerialNumber, LocalDateTime expiryDate) {
        this();
        this.token = token;
        this.memberSerialNumber = memberSerialNumber;
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getMemberSerialNumber() { return memberSerialNumber; }
    public void setMemberSerialNumber(Long memberSerialNumber) { this.memberSerialNumber = memberSerialNumber; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
