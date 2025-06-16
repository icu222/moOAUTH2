package com.healthsync.user.service.Oauth;

import com.healthsync.user.domain.Oauth.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long memberSerialNumber);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByMemberSerialNumber(Long memberSerialNumber);
    void deleteExpiredTokens();
}