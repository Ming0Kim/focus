package com.bb.focus.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProceedingCompanyRes")
@NoArgsConstructor
public class ProceedingCompanyRes {

  @ApiModelProperty(name = "기업명", example = "삼성")
  private String companyName;

  @ApiModelProperty(name = "기업의 진행중인 프로세스 리스트")
  private List<ProceedingProcess> proceedingProcessList;

}

@Getter
@Setter
@NoArgsConstructor
class ProceedingProcess{

  @ApiModelProperty(name = "전형 이름", example = "IT: 백엔드")
  private String processName;

  @ApiModelProperty(name = "전형 시작일", example = "")
  private LocalDateTime startDate;

  @ApiModelProperty(name = "전형 종료일", example = "")
  private LocalDateTime endDate;
}
