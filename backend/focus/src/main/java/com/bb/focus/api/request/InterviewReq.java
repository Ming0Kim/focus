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
@ApiModel("InterviewReq")
public class InterviewReq {

  @NotNull(message="차수를 입력해주세요.")
  @ApiModelProperty(name = "차수", example = "1")
  private Byte step;

  @NotBlank(message="면접 이름을 입력해주세요.")
  @ApiModelProperty(name = "면접 이름", example = "두나무 FE1")
  private String name;

  @NotBlank(message="면접 시작 일시를 입력해주세요.")
  @ApiModelProperty(name = "면접 시작 일시", example = "2021-11-09T11:44:30.327959")
  private LocalDateTime startDate;


  @NotBlank(message="면접 종료 일시를 입력해주세요.")
  @ApiModelProperty(name = "면접 종료 일시", example = "2021-11-09T11:44:30.327959")
  private LocalDateTime endDate;

  @NotNull(message = "평가지 시퀀스 넘버를 입력해주세요.")
  @ApiModelProperty(name = "평가지 시퀀스 넘버", example = "1")
  private Long evaluationSheetId;

}
