package com.healthsync.user.repository.entity;

import com.healthsync.user.domain.Oauth.RefreshToken;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens", schema = "user_service")
@EntityListeners(AuditingEntityListener.class)
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 500)
    private String token;

    @Column(name = "member_serial_number", nullable = false)
    private Long memberSerialNumber;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    protected RefreshTokenEntity() {}

    public RefreshTokenEntity(String token, Long memberSerialNumber, LocalDateTime expiryDate) {
        this.token = token;
        this.memberSerialNumber = memberSerialNumber;
        this.expiryDate = expiryDate;
    }

    public static RefreshTokenEntity fromDomain(RefreshToken refreshToken) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.id = refreshToken.getId();
        entity.token = refreshToken.getToken();
        entity.memberSerialNumber = refreshToken.getMemberSerialNumber();
        entity.expiryDate = refreshToken.getExpiryDate();
        entity.createdAt = refreshToken.getCreatedAt();
        return entity;
    }

    public RefreshToken toDomain() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(this.id);
        refreshToken.setToken(this.token);
        refreshToken.setMemberSerialNumber(this.memberSerialNumber);
        refreshToken.setExpiryDate(this.expiryDate);
        refreshToken.setCreatedAt(this.createdAt);
        return refreshToken;
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
