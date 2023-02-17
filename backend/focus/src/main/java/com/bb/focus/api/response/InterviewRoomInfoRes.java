package com.bb.focus.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("InterviewRoomInfoRes")
@NoArgsConstructor
public class InterviewRoomInfoRes {

  @ApiModelProperty(name = "인터뷰 시퀀스 넘버")
  private Long interviewId;

  @ApiModelProperty(name = "차수")
  private Byte step;

  public InterviewRoomInfoRes(Long interviewId, Byte step) {
    this.interviewId = interviewId;
    this.step = step;
  }
}
