package com.healthsync.user.controller;

import com.healthsync.user.domain.HealthCheck.HealthCheckupRaw;
import com.healthsync.user.domain.HealthCheck.HealthNormalRange;
import com.healthsync.user.dto.HealthCheck.HealthProfileSummaryResponse;
import com.healthsync.user.service.HealthProfile.HealthProfileService;
import com.healthsync.common.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health/profile")
@Tag(name = "건강 프로필", description = "건강 프로필 관리 API")
@SecurityRequirement(name = "Bearer Authentication")
public class HealthCheckupController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckupController.class);

    private final HealthProfileService healthProfileService;
    private final JwtUtil jwtUtil;

    public HealthCheckupController(HealthProfileService healthProfileService, JwtUtil jwtUtil) {
        this.healthProfileService = healthProfileService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/summary")
    @Operation(
            summary = "건강 프로필 요약 조회",
            description = "사용자의 최근 건강검진 데이터와 정상 범위를 함께 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "건강 프로필 요약 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 - 유효하지 않은 토큰"),
            @ApiResponse(responseCode = "404", description = "건강검진 데이터를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<HealthProfileSummaryResponse>> getHealthProfileSummary(
            @AuthenticationPrincipal Jwt jwt) {

        logger.info("건강 프로필 요약 조회 요청");

        // JWT에서 사용자 정보 추출
        String name = jwtUtil.getNameFromJwt(jwt);
        LocalDate birthDate = jwtUtil.getBirthDateFromJwt(jwt);
        Long memberSerialNumber = jwtUtil.getMemberSerialNumberFromJwt(jwt);

        logger.info("사용자 정보 - Member Serial Number: {}, 이름: {}, 생년월일: {}",
                memberSerialNumber, name, birthDate);

        // 생년월일이 토큰에 없는 경우
        if (birthDate == null) {
            logger.warn("JWT 토큰에 생년월일 정보가 없음 - Member Serial Number: {}", memberSerialNumber);
            return com.healthsync.common.response.ResponseHelper.badRequest(
                    "사용자 생년월일 정보가 필요합니다. 프로필을 완성해주세요.",
                    "BIRTH_DATE_REQUIRED"
            );
        }

        // 최근 건강검진 데이터 조회
        Optional<HealthCheckupRaw> recentCheckup = healthProfileService.getMostRecentHealthCheckup(name, birthDate);

        if (recentCheckup.isEmpty()) {
            logger.warn("건강검진 데이터를 찾을 수 없음 - 이름: {}, 생년월일: {}", name, birthDate);
            return com.healthsync.common.response.ResponseHelper.notFound(
                    "건강검진 데이터를 찾을 수 없습니다.",
                    "HEALTH_CHECKUP_NOT_FOUND"
            );
        }

        // 정상 범위 데이터 조회
        List<HealthNormalRange> normalRanges = healthProfileService.getAllHealthNormalRanges();

        // 응답 객체 생성
        HealthProfileSummaryResponse response = new HealthProfileSummaryResponse(
                recentCheckup.get(),
                normalRanges
        );

        logger.info("건강 프로필 요약 조회 성공 - 검진년도: {}, 정상범위 {}건",
                recentCheckup.get().getReferenceYear(), normalRanges.size());

        return com.healthsync.common.response.ResponseHelper.success(
                response,
                "건강 프로필 요약 조회 성공"
        );
    }
}