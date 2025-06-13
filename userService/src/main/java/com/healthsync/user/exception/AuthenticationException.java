package com.healthsync.user.exception;

import com.healthsync.common.exception.CustomException;

public class AuthenticationException extends CustomException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
