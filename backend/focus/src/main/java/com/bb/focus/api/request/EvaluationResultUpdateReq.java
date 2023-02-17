package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("EvaluationResultReq")
public class EvaluationResultUpdateReq {

  @ApiModelProperty(name="평가 결과 시퀀스 넘버")
  Long evaluationResultId;

  @ApiModelProperty(name="평가 결과", example = "전문성이 부족해보임 자료구조에 대한 이해도가 부족함.") // 평가자가 쓴 메모
  @NotBlank(message = "평과 결과를 입력해주세요.")
  String content;

  @ApiModelProperty(name="평가 점수", example = "100")
  byte score;

}
