// src/main/java/com/healthsync/user/controller/AuthController.java - 수정된 버전
package com.healthsync.user.controller;

import com.healthsync.user.domain.User;
import com.healthsync.user.domain.RefreshToken;
import com.healthsync.user.dto.TokenResponse;
import com.healthsync.user.dto.TokenRefreshRequest;
import com.healthsync.user.service.JwtTokenService;
import com.healthsync.user.service.UserService;
import com.healthsync.user.service.RefreshTokenService;
import com.healthsync.user.exception.UserNotFoundException;
import com.healthsync.user.exception.AuthenticationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증", description = "사용자 인증 관련 API")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService, JwtTokenService jwtTokenService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "토큰 갱신",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급받습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 리프레시 토큰"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<TokenResponse>> refreshToken(
            @Parameter(description = "리프레시 토큰 요청 객체", required = true)
            @Valid @RequestBody TokenRefreshRequest request) {

        logger.info("토큰 갱신 요청 - Refresh Token: {}", request.getRefreshToken().substring(0, 8) + "...");

        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> {
                    logger.warn("유효하지 않은 리프레시 토큰: {}", requestRefreshToken.substring(0, 8) + "...");
                    return new AuthenticationException("유효하지 않은 리프레시 토큰입니다");
                });

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        RefreshToken finalRefreshToken = refreshToken;
        User user = userService.findById(refreshToken.getMemberSerialNumber())
                .orElseThrow(() -> {
                    logger.error("리프레시 토큰의 사용자를 찾을 수 없음 - Member Serial Number: {}", finalRefreshToken.getMemberSerialNumber());
                    return new UserNotFoundException("사용자를 찾을 수 없습니다");
                });

        logger.info("토큰 갱신 대상 사용자 - Member Serial Number: {}, Google ID: {}", user.getMemberSerialNumber(), user.getGoogleId());

        String newAccessToken = jwtTokenService.generateAccessToken(user);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getMemberSerialNumber());

        TokenResponse tokenResponse = new TokenResponse(
                newAccessToken,
                newRefreshToken.getToken()
        );

        logger.info("토큰 갱신 완료 - Member Serial Number: {}", user.getMemberSerialNumber());

        return com.healthsync.common.response.ResponseHelper.success(tokenResponse, "토큰 갱신 성공");
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "현재 사용자를 로그아웃합니다. 모든 리프레시 토큰이 삭제됩니다."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<Void>> logout(@AuthenticationPrincipal Jwt jwt) {
        Long memberSerialNumber = Long.valueOf(jwt.getSubject());
        String googleId = jwt.getClaimAsString("googleId");

        logger.info("로그아웃 요청 - Member Serial Number: {}, Google ID: {}", memberSerialNumber, googleId);

        // 해당 사용자의 모든 리프레시 토큰 삭제
        refreshTokenService.deleteByMemberSerialNumber(memberSerialNumber);

        logger.info("로그아웃 완료 - Member Serial Number: {}", memberSerialNumber);

        return com.healthsync.common.response.ResponseHelper.success(null, "로그아웃 성공");
    }

    @PostMapping("/logout/{memberSerialNumber}")
    @Operation(
            summary = "특정 사용자 강제 로그아웃",
            description = "관리자가 특정 사용자를 강제 로그아웃합니다. 해당 사용자의 모든 리프레시 토큰이 삭제됩니다.",
            hidden = true
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강제 로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "대상 사용자를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<Void>> forceLogout(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "강제 로그아웃할 사용자의 회원 일련번호", required = true, example = "1")
            @PathVariable Long memberSerialNumber) {

        Long currentUserSerialNumber = Long.valueOf(jwt.getSubject());
        String currentGoogleId = jwt.getClaimAsString("googleId");

        logger.info("강제 로그아웃 요청 - 요청자: {} ({}), 대상: {}", currentUserSerialNumber, currentGoogleId, memberSerialNumber);

        // 대상 사용자가 존재하는지 확인
        User targetUser = userService.findById(memberSerialNumber)
                .orElseThrow(() -> new UserNotFoundException("대상 사용자를 찾을 수 없습니다: " + memberSerialNumber));

        // 자기 자신을 로그아웃하는 경우 방지 (일반 로그아웃 사용 권장)
        if (currentUserSerialNumber.equals(memberSerialNumber)) {
            logger.warn("자기 자신에 대한 강제 로그아웃 시도 - Member Serial Number: {}", currentUserSerialNumber);
            return com.healthsync.common.response.ResponseHelper.badRequest(
                    "자기 자신에 대해서는 일반 로그아웃을 사용해주세요",
                    "SELF_LOGOUT_NOT_ALLOWED"
            );
        }

        // 대상 사용자의 모든 리프레시 토큰 삭제
        refreshTokenService.deleteByMemberSerialNumber(memberSerialNumber);

        logger.info("강제 로그아웃 완료 - 대상: {} ({})", targetUser.getMemberSerialNumber(), targetUser.getGoogleId());

        return com.healthsync.common.response.ResponseHelper.success(null, "강제 로그아웃 성공");
    }

    @GetMapping("/verify")
    @Operation(
            summary = "토큰 검증",
            description = "현재 JWT 토큰의 유효성을 검증하고 사용자 정보를 반환합니다."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 검증 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<TokenVerificationResponse>> verifyToken(@AuthenticationPrincipal Jwt jwt) {
        Long memberSerialNumber = Long.valueOf(jwt.getSubject());
        String googleId = jwt.getClaimAsString("googleId");
        String name = jwt.getClaimAsString("name");

        logger.debug("토큰 검증 요청 - Member Serial Number: {}, Google ID: {}", memberSerialNumber, googleId);

        // 사용자 존재 여부 확인 (DB에서 삭제된 사용자의 토큰인지 체크)
        boolean userExists = userService.findById(memberSerialNumber).isPresent();

        if (!userExists) {
            logger.warn("삭제된 사용자의 토큰 - Member Serial Number: {}", memberSerialNumber);
            throw new UserNotFoundException("사용자가 존재하지 않습니다");
        }

        TokenVerificationResponse response = new TokenVerificationResponse(
                memberSerialNumber,
                googleId,
                name,
                jwt.getIssuedAt(),
                jwt.getExpiresAt()
        );

        return com.healthsync.common.response.ResponseHelper.success(response, "토큰 검증 성공");
    }

    // 토큰 검증 응답 DTO
    public static class TokenVerificationResponse {
        private Long memberSerialNumber;
        private String googleId;
        private String name;
        private java.time.Instant issuedAt;
        private java.time.Instant expiresAt;

        public TokenVerificationResponse(Long memberSerialNumber, String googleId, String name,
                                         java.time.Instant issuedAt, java.time.Instant expiresAt) {
            this.memberSerialNumber = memberSerialNumber;
            this.googleId = googleId;
            this.name = name;
            this.issuedAt = issuedAt;
            this.expiresAt = expiresAt;
        }

        // Getters
        public Long getMemberSerialNumber() { return memberSerialNumber; }
        public String getGoogleId() { return googleId; }
        public String getName() { return name; }
        public java.time.Instant getIssuedAt() { return issuedAt; }
        public java.time.Instant getExpiresAt() { return expiresAt; }
    }
}