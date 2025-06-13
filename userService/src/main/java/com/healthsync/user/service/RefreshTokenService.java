package com.healthsync.user.service;

import com.healthsync.user.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long memberSerialNumber);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByMemberSerialNumber(Long memberSerialNumber);
    void deleteExpiredTokens();
}