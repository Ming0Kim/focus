package com.bb.focus.api.response;

import com.bb.focus.db.entity.process.Process;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProcessRes")
@NoArgsConstructor
public class ProcessRes {

  @ApiModelProperty(name = "프로세스 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name = "프로세스 내 면접 총 진행 차수", example = "3")
  private Byte interviewCount;

  @ApiModelProperty(name = "프로세스 이름", example = "두나무 FE")
  private String name;

  @ApiModelProperty(name = "프로세스 시작일", example = "")
  private LocalDateTime startDate;

  @ApiModelProperty(name = "프로세스 종료일", example = "")
  private LocalDateTime endDate;

  @ApiModelProperty(name = "프로세스 내 현재 면접 진행 차수", example = "1")
  private Byte currentStep;

  public ProcessRes(Long id, String name, LocalDateTime startDate,
      LocalDateTime endDate, Byte interviewCount, Byte currentStep) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.interviewCount = interviewCount;
    this.currentStep = currentStep;
  }

  public void GetProcess(Process process){
    id = process.getId();
    interviewCount = process.getInterviewCount();
    name = process.getName();
    startDate = process.getStartDate();
    endDate = process.getEndDate();
    currentStep = process.getCurrentStep();
  }
}
