package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluationResultReq")
public class EvaluationResultReq {

  @ApiModelProperty(name="평가 결과", example = "전문성이 부족해보임 자료구조에 대한 이해도가 부족함.") // 평가자가 쓴 메모
  @NotBlank(message = "평과 결과를 입력해주세요.")
  String Content;

  @ApiModelProperty(name="평가 점수", example = "100")
  byte score;

}
