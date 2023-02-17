package com.bb.focus.api.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class EvaluationSheetReq {

  @Getter
  @Setter
  public static class Create{
    @ApiModelProperty(name = "평가지 제목", example = "제목제목")
    @NotBlank(message = "평가지 제목을 입력해주세요.")
    @Size(max = 30, message = "평가지 제목은 30자 이내로 입력해주세요.")
    private String sheetName;
  }

}
