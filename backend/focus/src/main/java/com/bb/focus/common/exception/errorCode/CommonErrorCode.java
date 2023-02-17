package com.bb.focus.common.exception.errorCode;

import com.bb.focus.common.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "유효하지 않은 변수가 포함되었지롱"),
  RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "오이시쿠나레 오이시쿠나레 모ㅇ.. 앗.. 리소스가 존재하지 않는다구!!"),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "응 내부 에러^^"),
  INVALID_PARAMETER_MAYBE_DATETIME(HttpStatus.BAD_REQUEST, "유효하지 않은 변수가 포함되었음!! DateTime 특히 주의"),
  ;

  private final HttpStatus httpStatus;
  private final String message;
}

