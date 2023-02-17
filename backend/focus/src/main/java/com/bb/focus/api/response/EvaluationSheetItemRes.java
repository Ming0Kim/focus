package com.bb.focus.api.response;

import com.bb.focus.db.entity.evaluation.EvaluationItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluationSheetItemRes")
public class EvaluationSheetItemRes {

    public EvaluationSheetItemRes(EvaluationItem item){
        evaluationSheetId = item.getEvaluationSheet().getId();
        id = item.getId();
        content = item.getScaleContent();
        score = item.getScoreRange();
    }



    @ApiModelProperty(name="평가지 시퀀스 넘버", example = "1")
    private Long evaluationSheetId;
    @ApiModelProperty(name="평가항목 아이템", example = "1")
    private Long id;

    @ApiModelProperty(name="평가항목 제목", example = "성실성")
    private String content;

    @ApiModelProperty(name="평가항복 최대점수", example = "20")
    private Byte score;
}
