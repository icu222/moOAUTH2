package com.healthsync.common.exception;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.common.response.ResponseHelper;
import com.healthsync.user.exception.AuthenticationException;
import com.healthsync.user.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException e) {
        logger.error("User not found: {}", e.getMessage());
        return ResponseHelper.notFound("사용자를 찾을 수 없습니다", e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException e) {
        logger.error("Authentication error: {}", e.getMessage());
        return ResponseHelper.unauthorized("인증에 실패했습니다", e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        logger.error("Access denied: {}", e.getMessage());
        return ResponseHelper.forbidden("접근이 거부되었습니다", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception e) {
        logger.error("Unexpected error: {}", e.getMessage(), e);
        return ResponseHelper.internalServerError("서버 오류가 발생했습니다", e.getMessage());
    }
}
