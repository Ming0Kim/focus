package com.bb.focus.api.response;

import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("applicantLogRes")
public class ApplicantLogRes {

  @ApiModelProperty(name="합격로그 시퀀스 넘버", example = "1")
  private Long id;
  @ApiModelProperty(name="지원자 시퀀스 넘버", example = "1")
  private Long applicantId;
  @ApiModelProperty(name="지원자 이름", example = "1")
  private String applicantName;


  public ApplicantLogRes(ApplicantPassLog applicantPassLog){
    id = applicantPassLog.getId();
    applicantId = applicantPassLog.getApplicant().getId();
    applicantName = applicantPassLog.getName();
  }

}
