package com.healthsync.user.controller;

import com.healthsync.user.domain.User;
import com.healthsync.user.dto.UserProfileResponse;
import com.healthsync.user.dto.UserUpdateRequest;
import com.healthsync.user.service.UserService;
import com.healthsync.user.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/health")
@Tag(name = "사용자", description = "사용자 관리 API")
@SecurityRequirement(name = "Bearer Authentication")
public class HealthProfileController {

    private final HealthProfileService healthProfileService;

    public HealthProfileController(HealthProfileService healthProfileService) {
        this.healthProfileService = healthProfileService;
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 사용자의 프로필 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패 - 유효하지 않은 토큰"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<UserProfileResponse>> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        Long memberSerialNumber = Long.valueOf(jwt.getSubject());

        User user = userService.findById(memberSerialNumber)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + memberSerialNumber));

        UserProfileResponse response = new UserProfileResponse(user);
        return com.healthsync.common.response.ResponseHelper.success(response, "사용자 정보 조회 성공");
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "사용자 정보 조회 : 안 씀.",
            description = "특정 사용자의 프로필 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<UserProfileResponse>> getUserById(
            @Parameter(description = "조회할 사용자의 회원 일련번호", required = true, example = "1")
            @PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));

        UserProfileResponse response = new UserProfileResponse(user);
        return com.healthsync.common.response.ResponseHelper.success(response, "사용자 정보 조회 성공");
    }

    @PostMapping("/register")
    @Operation(
            summary = "내 정보 수정",
            description = "현재 로그인한 사용자의 프로필 정보를 수정합니다. 이름, 생년월일, 직업을 수정할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 입력값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<UserProfileResponse>> updateCurrentUser(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "사용자 정보 수정 요청 객체", required = true)
            @Valid @RequestBody UserUpdateRequest request) {

        Long memberSerialNumber = Long.valueOf(jwt.getSubject());

        User user = userService.findById(memberSerialNumber)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + memberSerialNumber));

        user.setName(request.getName());
        user.setBirthDate(request.getBirthDate());
        user.setOccupation(request.getOccupation());

        User updatedUser = userService.updateUser(user);
        UserProfileResponse response = new UserProfileResponse(updatedUser);

        return com.healthsync.common.response.ResponseHelper.success(response, "사용자 정보 업데이트 성공");
    }


    @GetMapping("/healthProfile/{id}")
    @Operation(
            summary = "최근 건강검진 데이터 불러오기",
            description = "국민건강보험공단 데이터로부터 최근 건강검진 데이터를 불러 옵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "건강검진 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "건강검진 정보를 찾을 수 없음")
    })
    public ResponseEntity<com.healthsync.common.dto.ApiResponse<UserProfileResponse>> getUserHealthProfileById(
            @Parameter(description = "조회할 사용자의 회원 일련번호", required = true, example = "1")
            @PathVariable Long id) {

        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));

        UserProfileResponse response = new UserProfileResponse(user);
        return com.healthsync.common.response.ResponseHelper.success(response, "사용자 정보 조회 성공");
    }

}