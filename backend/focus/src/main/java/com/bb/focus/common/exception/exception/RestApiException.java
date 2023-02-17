package com.bb.focus.common.exception.exception;

import com.bb.focus.common.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{

  private final ErrorCode errorCode;

}
