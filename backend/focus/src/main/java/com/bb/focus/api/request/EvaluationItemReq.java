package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluationItemReq")
public class EvaluationItemReq {

    @ApiModelProperty(name="평가 항목이름", example = "성실성")
    @NotBlank(message = "평가 항목 이름을 입력해주세요.")
    @Size(max = 500, message = "평가 항목 이름은 500자 이내로 입력해주세요.")
    private String content;

    @ApiModelProperty(name="평가항목 최대점수", example = "100")
    @NotNull
    private Byte score;

}
