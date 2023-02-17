package com.bb.focus.api.response;

import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluationSheetRes")
public class EvaluationSheetRes {

  @ApiModelProperty(name="평가지 id", example = "1")
  private Long id;
  @ApiModelProperty(name="평가지 제목", example = "삼성전자 FE 직군 평가지")
  private String content;

  public EvaluationSheetRes(EvaluationSheet evaluationSheet){
    id = evaluationSheet.getId();
    content = evaluationSheet.getName();
  }
}
