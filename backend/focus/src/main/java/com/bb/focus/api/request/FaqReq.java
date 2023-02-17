package com.bb.focus.api.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * FAQ 관련 API ([POST] /login) 요청에 필요한 리퀘스트 바디 정의.
 */

public class FaqReq {

  @Getter
  @Setter
  public static class Create {

    @NotNull(message="서비스 관리자의 시퀀스 넘버를 입력해주세요.")
    @ApiModelProperty(name = "작성한 서비스 관리자 시퀀스 넘버", example = "1")
    private Long serviceAdminId;

    @NotBlank(message="faq의 제목을 입력해주세요.")
    @ApiModelProperty(name = "faq 제목", example = "제목제목")
    private String title;

    @NotBlank(message="faq의 내용을 입력해주세요.")
    @ApiModelProperty(name = "faq 내용", example = "내용내용")
    private String content;

    @Min(3)
    @Max(4)
    @NotNull(message="3:기업관리자,평가자 열람 가능, 4:기업관리자,지원자 열람 가능입니다. 3, 4 중 하나를 입력해주세요.")
    @ApiModelProperty(name = "3:기업관리자,평가자 열람 가능, 4:기업관리자,지원자 열람 가능", example = "3")
    private Byte authRange;


  }


}
