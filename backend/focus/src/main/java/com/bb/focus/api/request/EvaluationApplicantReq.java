package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import lombok.ToString;

@ToString
@Getter
@Setter
@ApiModel("EvaluationApplicantReq")
public class EvaluationApplicantReq {

    @ApiModelProperty(name = "지원자 시퀀스 넘버", example = "1")
    @NotNull(message = "지원자의 시퀀스 넘버를 입력해주세요.")
    private Long applicantId;

    @ApiModelProperty(name = "해당 지원자에 대한 평가자의 평가 내용 전체")
    private List<EvaluationInfo> evaluationItemInfoList;
    @ApiModelProperty(name = "면접실 시퀀스 넘버", example = "1")
    @NotNull(message = "면접실의 시퀀스 넘버를 입력해주세요.")
    private Long interviewRoomId;

    @ApiModelProperty(name = "해당 지원자에 대한 평가자의 메모", example = "잘함")
    private String memo;



}
