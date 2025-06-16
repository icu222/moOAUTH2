package com.healthsync.common.util;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class JwtUtil {

    private final JwtDecoder jwtDecoder;

    public JwtUtil(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public Jwt parseToken(String token) {
        return jwtDecoder.decode(token);
    }

    public String getUserIdFromToken(String token) {
        Jwt jwt = parseToken(token);
        return jwt.getSubject();
    }

    public String getEmailFromToken(String token) {
        Jwt jwt = parseToken(token);
        return jwt.getClaimAsString("email");
    }

    public String getNameFromToken(String token) {
        Jwt jwt = parseToken(token);
        return jwt.getClaimAsString("name");
    }

    public String getRoleFromToken(String token) {
        Jwt jwt = parseToken(token);
        return jwt.getClaimAsString("role");
    }

    /**
     * JWT 토큰에서 생년월일 추출
     */
    public LocalDate getBirthDateFromToken(String token) {
        Jwt jwt = parseToken(token);
        String birthDateStr = jwt.getClaimAsString("birthDate");
        if (birthDateStr != null) {
            return LocalDate.parse(birthDateStr);
        }
        return null;
    }

    /**
     * JWT 토큰에서 생년월일 추출 (Jwt 객체 사용)
     */
    public LocalDate getBirthDateFromJwt(Jwt jwt) {
        String birthDateStr = jwt.getClaimAsString("birthDate");
        if (birthDateStr != null) {
            return LocalDate.parse(birthDateStr);
        }
        return null;
    }

    /**
     * JWT 토큰에서 이름 추출 (Jwt 객체 사용)
     */
    public String getNameFromJwt(Jwt jwt) {
        return jwt.getClaimAsString("name");
    }

    /**
     * JWT 토큰에서 Google ID 추출 (Jwt 객체 사용)
     */
    public String getGoogleIdFromJwt(Jwt jwt) {
        return jwt.getClaimAsString("googleId");
    }

    /**
     * JWT 토큰에서 사용자 ID 추출 (Jwt 객체 사용)
     */
    public Long getMemberSerialNumberFromJwt(Jwt jwt) {
        return Long.valueOf(jwt.getSubject());
    }
}