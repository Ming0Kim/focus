package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@ApiModel("EvaluationItemInfo")
public class EvaluationInfo {
  @ApiModelProperty(name = "평가 항목 시퀀스 넘버", example = "1")
  @NotBlank(message = "평가 항목 시퀀스 넘버를 입력해주세요.")
  private Long evaluationItemId;

  @ApiModelProperty(name = "평가 내용", example = "1")
  private String content;

  @ApiModelProperty(name = "면접실 시퀀스 넘버", example = "1")
  private byte score;

}
