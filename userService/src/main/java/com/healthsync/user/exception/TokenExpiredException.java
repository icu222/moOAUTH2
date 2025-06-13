package com.healthsync.user.exception;

import com.healthsync.common.exception.CustomException;

public class TokenExpiredException extends CustomException {
  public TokenExpiredException(String message) {
    super(message);
  }

  public TokenExpiredException(String message, Throwable cause) {
    super(message, cause);
  }
}