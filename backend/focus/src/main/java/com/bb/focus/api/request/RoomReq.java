package com.bb.focus.api.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 관련 API ([POST] /login) 요청에 필요한 리퀘스트 바디 정의.
 */

public class RoomReq {

  @Getter
  @Setter
  public static class Create {

    @NotNull(message = "면접 시퀀스 넘버를 입력해주세요.")
    @ApiModelProperty(name = "면접 ID", example = "1")
    Long interviewId;
  }


}
