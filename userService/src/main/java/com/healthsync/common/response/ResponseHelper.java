package com.healthsync.common.response;

import com.healthsync.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message, String error) {
        return ResponseEntity.badRequest().body(ApiResponse.error(message, error));
    }

    public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message, String error) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(message, error));
    }

    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message, String error) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(message, error));
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message, String error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(message, error));
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message, String error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(message, error));
    }
}
