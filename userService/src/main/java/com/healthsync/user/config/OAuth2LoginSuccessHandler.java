package com.healthsync.user.config;

import com.healthsync.user.domain.User;
import com.healthsync.user.domain.RefreshToken;
import com.healthsync.user.dto.LoginResponse;
import com.healthsync.user.dto.UserProfileResponse;
import com.healthsync.user.service.JwtTokenService;
import com.healthsync.user.service.UserService;
import com.healthsync.user.service.RefreshTokenService;
import com.healthsync.common.dto.ApiResponse;
import com.healthsync.common.response.ResponseHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    public OAuth2LoginSuccessHandler(UserService userService,
                                     JwtTokenService jwtTokenService,
                                     RefreshTokenService refreshTokenService,
                                     ObjectMapper objectMapper) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("OAuth2 로그인 성공 처리 시작");

        if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            try {
                String googleId = oauth2User.getAttribute("sub");
                String name = oauth2User.getAttribute("name");

                logger.info("OAuth2 사용자 정보 - Google ID: {}, Name: {}", googleId, name);

                // 기존 사용자 확인
                Optional<User> existingUser = userService.findByGoogleId(googleId);
                boolean isNewUser = existingUser.isEmpty();

                User user;
                if (isNewUser) {
                    logger.info("신규 사용자 생성 - Google ID: {}", googleId);

                    // 새 사용자 생성 (memberSerialNumber는 null로 두어 BIGSERIAL이 자동 생성)
                    User newUser = new User(googleId, name, LocalDate.of(1900, 1, 1), null);
                    user = userService.saveUser(newUser);

                    logger.info("신규 사용자 저장 완료 - Member Serial Number: {}", user.getMemberSerialNumber());
                } else {
                    user = existingUser.get();
                    logger.info("기존 사용자 로그인 - Member Serial Number: {}", user.getMemberSerialNumber());

                    // 기존 사용자의 최종 로그인 시간 업데이트
                    userService.updateLastLoginAt(user.getMemberSerialNumber());

                    // 업데이트된 사용자 정보 다시 조회
                    user = userService.findById(user.getMemberSerialNumber()).orElse(user);
                }

                // JWT 토큰 생성
                String accessToken = jwtTokenService.generateAccessToken(user);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getMemberSerialNumber());

                // 응답 객체 생성 (isNewUser 포함)
                LoginResponse loginResponse = new LoginResponse(
                        accessToken,
                        refreshToken.getToken(),
                        new UserProfileResponse(user),
                        isNewUser
                );

                // ResponseHelper를 사용하여 응답 생성
                String successMessage = isNewUser ? "회원가입 및 로그인 성공" : "로그인 성공";
                ResponseEntity<ApiResponse<LoginResponse>> responseEntity =
                        ResponseHelper.success(loginResponse, successMessage);

                // JSON 응답 반환
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(responseEntity.getStatusCode().value());

                String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();

                logger.info("OAuth2 로그인 처리 완료 - Member Serial Number: {}, 신규 사용자: {}", user.getMemberSerialNumber(), isNewUser);

            } catch (Exception e) {
                logger.error("OAuth2 로그인 처리 중 오류 발생", e);
                handleError(response, "로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
            }
        } else {
            logger.error("OAuth2User 정보를 가져올 수 없음");
            handleError(response, "OAuth2 인증 정보를 찾을 수 없습니다");
        }
    }

    private void handleError(HttpServletResponse response, String message) throws IOException {
        try {
            ResponseEntity<ApiResponse<Void>> errorResponse =
                    ResponseHelper.badRequest(message, message);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(errorResponse.getStatusCode().value());

            String jsonResponse = objectMapper.writeValueAsString(errorResponse.getBody());
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e) {
            logger.error("에러 응답 생성 중 오류 발생", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false,\"message\":\"서버 오류가 발생했습니다\"}");
        }
    }
}