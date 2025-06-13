package com.healthsync.common.util;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

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
}
