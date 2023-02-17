package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ApiModel("InterviewRoomReq")
public class InterviewRoomReq {

  @NotBlank(message="면접실 이름을 입력해주세요.")
  @ApiModelProperty(name = "면접실 이름", example = "두나무 FE1-1")
  private String name;

  @NotNull(message="면접 일정 시작 시간을 입력해주세요.")
  @ApiModelProperty(name = "실제 면접 시작 시간", example = "2021-11-09T11:44:30.327959")
  private LocalDateTime startTime;

  @NotNull(message="면접 일정 종료 시간을 입력해주세요.")
  @ApiModelProperty(name = "실제 면접 종료 시간", example = "2021-11-09T11:44:30.327959")
  private LocalDateTime endTime;

  @NotNull(message = "해당 면접 일정이 포함된 면접(차수)의 시퀀스 넘버를 입력해주세요.")
  @ApiModelProperty(name = "면접(차수) 시퀀스 넘버", example = "1")
  private Long interviewId;

  @NotNull(message = "해당 면접실에 들어가는 평가자 시퀀스 넘버의 리스트를 띄어쓰기 없이 입력해주세요.")
  @ApiModelProperty(name = "해당 면접실에 들어가는 평가자 시퀀스 넘버 리스트", example = "[1,2,3]")
  private Long[] evaluators;

  @NotNull(message = "해당 면접 일정에 들어가는 지원자 시퀀스 넘버 리스트를 띄어쓰기 없이 입력해주세요.")
  @ApiModelProperty(name = "해당 면접실에 들어가는 지원자 시퀀스 넘버 리스트", example = "[1,2,3]")
  private Long[] applicants;
}
