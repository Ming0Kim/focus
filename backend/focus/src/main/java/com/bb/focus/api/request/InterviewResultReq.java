package com.bb.focus.api.request;

import com.bb.focus.db.entity.applicant.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("InterviewResultReq")
public class InterviewResultReq {

  @NotNull(message="지원자 시퀀스 넘버를 입력해주세요.")
  @ApiModelProperty(name="지원자 시퀀스 넘버", example = "1")
  long applicantId;

  @NotBlank(message="지원자의 합불 여부(p, np)를 입력해주세요.")
  @ApiModelProperty(name="합불 여부", example = "p, np")
  String pass;


  public InterviewResultReq(Long applicantId, String pass){
    this.applicantId = applicantId;
    this.pass = pass;
  }
}
