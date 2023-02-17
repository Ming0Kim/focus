package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 지원자 계정 생성 API ([POST] /companyusers/applicants) 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ApiModel("ApplicantInfoReq")
public class ApplicantInfoReq {

  @ApiModelProperty(name="지원자 이름", example = "홍길동")
  @NotBlank(message = "지원자 이름을 입력해주세요.")
  @Size(max = 45, message = "지원자 이름은 45자 이내로 입력해주세요.")
  private String name;

  @ApiModelProperty(name="지원자 수험번호", example = "123456")
  @NotBlank(message = "지원자 수험번호를 입력해주세요.")
  @Size(max = 45, message = "지원자 수험번호는 45자 이내로 입력해주세요.")
  private String code;

  @ApiModelProperty(name="지원자 성별] m: 남성, f: 여성", example = "f")
  @NotBlank(message = "지원자 성별을 입력해주세요.")
  private String gender;

  @ApiModelProperty(name="지원자 생년월일", example = "1998-08-01")
  @NotNull(message = "지원자 생년월일을 입력해주세요.")
  private LocalDate birth;

  @ApiModelProperty(name="지원자 사진 url", example = "사진")
  private String image;

  @ApiModelProperty(name="지원자 이메일", example = "123@asd.com")
  @NotBlank(message = "지원자 이메일을 입력해주세요.")
  @Email(message = "이메일 형식을 준수해주세요.", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  private String email;

  @ApiModelProperty(name="지원자 휴대전화번호", example = "010-1111-2345")
  @NotBlank(message = "지원자 휴대전화번호를 입력해주세요.")
  @Size(max = 13, message = "지원자 휴대전화번호는 13자 이내로 입력해주세요.")
  @Pattern(message = "전화번호 형식을 준수해주세요.", regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$")
  private String tel;

  @ApiModelProperty(name="지원자 이력서 url", example = "이력서")
  private String resume;

  @ApiModelProperty(name="지원자 학위] b: 학사, m: 석사, d: 박사", example = "b")
  private String degree;

  @ApiModelProperty(name="수상 횟수", example = "1")
  private Byte awardCount;

  @ApiModelProperty(name="대외활동 횟수", example = "2")
  private Byte activityCount;

  @ApiModelProperty(name="2년제 대학교 시퀀스 넘버", example = "1")
  private Long collegeId;

  @ApiModelProperty(name="4년제 대학교 시퀀스 넘버", example = "0")
  private Long univId;

  @ApiModelProperty(name="대학원 시퀀스 넘버", example = "0")
  private Long graduateId;

  @ApiModelProperty(name="전공", example = "컴퓨터공학과")
  @NotBlank(message = "지원자 전공을 입력해주세요.")
  private String major;

  @ApiModelProperty(name="학점 만점", example = "4.5")
  @NotNull(message = "학점 만점 점수를 입력해주세요.")
  private Float totalCredit;

  @ApiModelProperty(name="학점", example = "3.5")
  @NotNull(message = "지원자 학점을 입력해주세요.")
  private Float credit;
}
