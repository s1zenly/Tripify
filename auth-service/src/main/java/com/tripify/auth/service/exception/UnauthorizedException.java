package com.tripify.auth.service.exception;

import com.tripify.auth.generated.model.ErrorCode;

public class UnauthorizedException extends RuntimeException {

  private final ErrorCode errorCode;

  public UnauthorizedException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
