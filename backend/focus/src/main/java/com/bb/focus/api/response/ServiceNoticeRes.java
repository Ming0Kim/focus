package com.bb.focus.api.response;

import com.bb.focus.db.entity.admin.ServiceNotice;
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
@ApiModel("ServiceNoticeRes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceNoticeRes {

  @ApiModelProperty(name = "서비스 공지사항 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name = "작성한 서비스 관리자의 시퀀스 넘버", example = "1")
  private Long serviceAdminId;

  @ApiModelProperty(name = "해당 글의 카테고리 시퀀스 넘버", example = "1")
  private String category;

  @ApiModelProperty(name = "제목", example = "1")
  private String title;

  @ApiModelProperty(name = "작성 시각", example = "1")
  private LocalDateTime createdAt;

  @ApiModelProperty(name = "내용", example = "1")
  private String content;

  public static ServiceNoticeRes from(ServiceNotice serviceNotice) {
    return ServiceNoticeRes.builder()
        .id(serviceNotice.getId())
        .serviceAdminId(serviceNotice.getServiceAdmin().getId())
        .category(serviceNotice.getCategory().getName())
        .title(serviceNotice.getTitle())
        .content(serviceNotice.getContent())
        .createdAt(serviceNotice.getCreatedAt())
        .build();
  }
}
