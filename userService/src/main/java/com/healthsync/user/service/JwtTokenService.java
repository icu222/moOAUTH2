package com.healthsync.user.service;

import com.healthsync.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenService(JwtEncoder jwtEncoder,
                           @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
                           @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.jwtEncoder = jwtEncoder;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(accessTokenExpiration, ChronoUnit.MILLIS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("healthsync")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(user.getMemberSerialNumber().toString())  // memberSerialNumber를 subject로
                .claim("googleId", user.getGoogleId())             // googleId
                .claim("name", user.getName())                     // name
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public long getAccessTokenExpirationTime() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpirationTime() {
        return refreshTokenExpiration;
    }
}