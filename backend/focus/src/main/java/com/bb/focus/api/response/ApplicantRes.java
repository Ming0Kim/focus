package com.bb.focus.api.response;

import com.bb.focus.db.entity.applicant.Applicant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ApplicantRes")
@NoArgsConstructor
public class ApplicantRes {
  @ApiModelProperty(name="지원자 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name="지원자 이름", example = "김싸피")
  private String name;

  @ApiModelProperty(name="지원자 아이디", example = "xxxAxxxx")
  private String userId;

  @ApiModelProperty(name="지원자 수험번호", example = "123456")
  private String code;

  @ApiModelProperty(name="지원자 사진 url", example = "url")
  private String image;

  @ApiModelProperty(name="지원자 전화번호", example = "010-1235-5456")
  private String tel;

  @ApiModelProperty(name="지원자 이메일", example = "abc@gmail.com")
  private String email;

  @ApiModelProperty(name="지원자 전공", example = "컴퓨터공학과")
  private String major;

  @ApiModelProperty(name="학점 만점", example = "4.5")
  private Float totalCredit;

  @ApiModelProperty(name="학점", example = "3.5")
  private Float credit;

  public ApplicantRes(Applicant applicant){
    id = applicant.getId();
    name = applicant.getName();
    userId = applicant.getUserId();
    code = applicant.getCode();
    image = applicant.getImage();
    major = applicant.getMajor();
    totalCredit = applicant.getTotalCredit();
    credit = applicant.getCredit();
    tel = applicant.getTel();
    email = applicant.getEmail();
  }

  public ApplicantRes(Long id, String name, String userId, String code, String image, String tel, String email, String major, float totalCredit, float credit) {
    this.id = id;
    this.name = name;
    this.userId = userId;
    this.code = code;
    this.image = image;
    this.tel = tel;
    this.email = email;
    this.major = major;
    this.totalCredit = totalCredit;
    this.credit = credit;
  }

  public static ApplicantRes of(Applicant user){
    ApplicantRes res = new ApplicantRes();
    res.setId(user.getId());
    res.setUserId(user.getUserId());
    res.setTel(user.getTel());
    res.setEmail(user.getEmail());
    res.setName(user.getName());
    res.setMajor(user.getMajor());
    res.setCode(user.getCode());
    res.setImage(user.getImage());
    res.setTotalCredit(user.getTotalCredit());
    res.setCredit(user.getCredit());
    return res;
  }

}
