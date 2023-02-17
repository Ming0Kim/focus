package com.bb.focus.api.response;

import com.bb.focus.db.entity.applicant.Applicant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ApplicantDetailRes")
public class ApplicantDetailRes {

  @ApiModelProperty(name="지원자 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name="지원자 이름", example = "김싸피")
  private String name;

  @ApiModelProperty(name="지원자 아이디", example = "xxxAxxxx")
  private String userId;

  @ApiModelProperty(name="지원자 수험번호", example = "123456")
  private String code;

  @ApiModelProperty(name="지원자 성별", example = "m: 남성, f: 여성")
  private String gender;

  @ApiModelProperty(name="지원자 생년월일", example = "1997-02-01")
  private LocalDate birth;

  @ApiModelProperty(name="지원자 사진 url", example = "url")
  private String image;

  @ApiModelProperty(name="지원자 이메일", example = "aaa@adsfa.com")
  private String email;

  @ApiModelProperty(name="지원자 전화번호", example = "010-1111-6766")
  private String tel;

  @ApiModelProperty(name="지원자 자기소개서 url", example = "url")
  private String resume;

  @ApiModelProperty(name="지원자 학위", example = "b: 학사, m: 석사, d: 박사")
  private String degree;

  @ApiModelProperty(name="지원자 수상횟수", example = "0")
  private int awardCount;

  @ApiModelProperty(name="지원자 대외활동 횟수", example = "0")
  private int activityCount;

  @ApiModelProperty(name="지원자 계정만료일", example = "")
  private LocalDateTime expireDate;

  @ApiModelProperty(name="지원자 전공", example = "컴퓨터공학과")
  private String major;

  @ApiModelProperty(name="학점 만점", example = "4.5")
  private Float totalCredit;

  @ApiModelProperty(name="학점", example = "3.5")
  private Float credit;

  public ApplicantDetailRes(Applicant applicant){
    id = applicant.getId();
    name = applicant.getName();
    userId = applicant.getUserId();
    code = applicant.getCode();
    gender = applicant.getGender();
    birth = applicant.getBirth();
    image = applicant.getImage();
    email = applicant.getEmail();
    tel = applicant.getTel();
    resume = applicant.getTel();
    degree = applicant.getDegree();
    awardCount = applicant.getAwardCount();
    activityCount = applicant.getActivityCount();
    expireDate = applicant.getExpireDate();
    major = applicant.getMajor();
    totalCredit = applicant.getTotalCredit();
    credit = applicant.getCredit();
  }


}
