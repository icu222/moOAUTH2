package com.healthsync.user.service.Oauth;

import com.healthsync.user.domain.Oauth.RefreshToken;
import com.healthsync.user.repository.entity.RefreshTokenEntity;
import com.healthsync.user.repository.jpa.RefreshTokenRepository;
import com.healthsync.user.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpiration;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public RefreshToken createRefreshToken(Long memberSerialNumber) {
        // 기존 리프레시 토큰 삭제
        refreshTokenRepository.deleteByMemberSerialNumber(memberSerialNumber);

        // 새 리프레시 토큰 생성
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);

        RefreshToken refreshToken = new RefreshToken(token, memberSerialNumber, expiryDate);
        RefreshTokenEntity entity = RefreshTokenEntity.fromDomain(refreshToken);
        RefreshTokenEntity savedEntity = refreshTokenRepository.save(entity);

        return savedEntity.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshTokenEntity::toDomain);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.deleteByMemberSerialNumber(token.getMemberSerialNumber());
            throw new AuthenticationException("리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.");
        }
        return token;
    }

    @Override
    public void deleteByMemberSerialNumber(Long memberSerialNumber) {
        refreshTokenRepository.deleteByMemberSerialNumber(memberSerialNumber);
    }

    @Override
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
