package com.bb.focus.common.exception.errorCode;

import com.bb.focus.common.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

  INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
  ;

  private final HttpStatus httpStatus;
  private final String message;
}