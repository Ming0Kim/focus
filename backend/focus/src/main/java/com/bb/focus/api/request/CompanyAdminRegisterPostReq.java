package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 기업 관리자 계정 생성 API
 */
@Getter
@Setter
@ApiModel("CompanyAdminRegisterPostRequest")
public class CompanyAdminRegisterPostReq {

  @ApiModelProperty(name = "기업 이름", example = "삼성")
  @NotBlank(message="회사 이름을 입력해주세요.")
  @Size(max = 45, message = "회사 이름은 45자 이내로 입력해주세요.")
  private String companyName;

  @ApiModelProperty(name = "계약 시작 날짜", example = "2022-01-23T00:00:00.000000")
  @NotNull(message = "계약 시작 날짜를 입력해주세요.")
  private LocalDateTime startDate;

  @ApiModelProperty(name = "계약 종료 날짜", example = "2025-01-23T00:00:00.000000")
  @NotNull(message = "계약 종료 날짜를 입력해주세요.")
  private LocalDateTime endDate;

  @ApiModelProperty(name = "기업 대표 전화 번호", example = "02-1234-1234")
  @NotBlank(message = "기업 대표 전화 번호를 입력해주세요.")
  @Size(max = 13, message = "기업 대표 전화번호는 13자 이내로 입력해주세요.")
  @Pattern(message = "전화번호 형식을 준수해주세요.", regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$")
  private String tel;

  @ApiModelProperty(name = "기업 대표 이메일", example = "samsung@samsung.com")
  @NotBlank(message = "기업 대표 이메일을 입력해주세요.")
  @Email(message = "이메일 형식을 준수해주세요.", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  private String email;

  @ApiModelProperty(name = "기업 관리자 이름", example = "이재용")
  @NotBlank(message = "기업 관리자 이름을 입력해주세요.")
  @Size(max = 10, message = "기업 관리자 이름은 10자 이내로 입력해주세요.")
  private String name;

  @ApiModelProperty(name = "기업 산업", example = "")
  @Size(max = 10, message = "기업 산업은 10자 이내로 입력해주세요.")
  private String industry;

  @ApiModelProperty(name = "기업 규모", example = "")
  @Size(max = 10, message = "기업 규모는 10자 이내로 입력해주세요.")
  private String size;

  @ApiModelProperty(name = "기업 로고 이미지", example = "")
  private String logoImage;

  @ApiModelProperty(name = "기업이 정한 면접실 배경색", example = "")
  private String skinColor;

  @ApiModelProperty(name = "유저 권한", example = "2")
  private Byte userRole;
}
