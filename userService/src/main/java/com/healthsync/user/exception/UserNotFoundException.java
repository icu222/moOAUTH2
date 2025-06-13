package com.healthsync.user.exception;

import com.healthsync.common.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
