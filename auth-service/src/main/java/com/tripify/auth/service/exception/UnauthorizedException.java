package com.tripify.auth.service.exception;


import com.tripify.auth.generated.model.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Getter
public class UnauthorizedException extends RuntimeException {

  private final ErrorCode errorCode;
  private final HttpHeaders httpHeaders;

  public UnauthorizedException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.httpHeaders = new HttpHeaders();
  }

  public UnauthorizedException(ErrorCode errorCode, HttpHeaders httpHeaders, String message) {
    super(message);
    this.errorCode = errorCode;
    this.httpHeaders = httpHeaders;
  }
}
