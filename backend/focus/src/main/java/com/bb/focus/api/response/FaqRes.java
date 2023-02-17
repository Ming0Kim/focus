package com.bb.focus.api.response;

import com.bb.focus.db.entity.admin.Faq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FaqRes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqRes {

  @ApiModelProperty(name = "faq 사항 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name = "작성한 서비스 관리자의 시퀀스 넘버", example = "1")
  private Long serviceAdminId;

  @ApiModelProperty(name = "제목", example = "1")
  private String title;

  @ApiModelProperty(name = "작성 시각", example = "1")
  private LocalDateTime createdAt;

  @ApiModelProperty(name = "내용", example = "1")
  private String content;

  @ApiModelProperty(name = "3:기업관리자,평가자 열람 가능, 4:기업관리자,지원자 열람 가능", example = "1")
  private Byte authRange;

  public static FaqRes from(Faq faq) {
    return FaqRes.builder()
        .id(faq.getId())
        .serviceAdminId(faq.getServiceAdmin().getId())
        .title(faq.getTitle())
        .createdAt(faq.getCreatedAt())
        .content(faq.getContent())
        .authRange(faq.getAuthRange())
        .build();
  }
}
