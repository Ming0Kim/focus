package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProcessReq")
public class ProcessReq {

  @NotNull(message = "프로세스의 면접 총 차수를 입력하세요.")
  @ApiModelProperty(name = "프로세스의 면접 총 차수", example = "3")
  private Byte interviewCount;

  @NotBlank(message = "프로세스 이름을 입력하세요.")
  @ApiModelProperty(name = "프로세스 이름", example = "두나무 개발자 FE")
  private String name;

  @ApiModelProperty(name = "프로세스 시작일", example = "2021-11-08T11:44:30.327959")
  private LocalDateTime startDate;

  @ApiModelProperty(name = "프로세스 종료일", example = "2021-11-09T11:44:30.327959")
  private LocalDateTime endDate;


}
