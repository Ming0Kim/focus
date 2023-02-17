package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Add Evaluator Request")
public class AddRemoveEvaluatorReq {

  @ApiModelProperty(name = "프로세스 시퀀스 넘버")
  @NotNull(message = "프로세스 시퀀스 넘버를 입력해주세요.")
  private Long processId;

  @ApiModelProperty(name = "면접실 시퀀스 넘버")
  @NotNull(message = "면접실 시퀀스 넘버를 입력해주세요.")
  private Long interviewRoomId;

  @ApiModelProperty(name = "평가자 시퀀스 넘버")
  @NotNull(message = "평가자 시퀀스 넘버를 입력해주세요.")
  private Long evaluatorId;

}
