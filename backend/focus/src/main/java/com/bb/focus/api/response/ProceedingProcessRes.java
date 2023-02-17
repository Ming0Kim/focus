package com.bb.focus.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("ProceedingProcess Res")
public class ProceedingProcessRes {

  @ApiModelProperty(name = "회사 이름", example = "삼성")
  private String companyName;

  @ApiModelProperty(name = "전형 이름", example = "IT: 백엔드")
  private String processName;

  @ApiModelProperty(name = "전형 시작일", example = "")
  private LocalDateTime startDate;

  @ApiModelProperty(name = "전형 종료일", example = "")
  private LocalDateTime endDate;

  public ProceedingProcessRes(String companyName, String processName, LocalDateTime startDate,
      LocalDateTime endDate) {
    this.companyName = companyName;
    this.processName = processName;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
