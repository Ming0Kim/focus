package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.crypto.Mac;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 평가자 계정 생성 API ([POST] /companyusers/evaluators) 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ApiModel("EvaluatorRegisterPostReq")
public class EvaluatorInfoReq {

  @ApiModelProperty(name="평가자 이름", example = "홍길동")
  @NotBlank(message = "평가자 이름을 입력해주세요.")
  @Size(max = 45, message = "평가자 이름은 45자 이내로 입력해주세요.")
  private String name;

  @ApiModelProperty(name="평가자 사번", example = "1111")
  @NotBlank(message = "평가자 사번을 입력해주세요.")
  @Size(max = 45, message = "평가자 사번은 45자 이내로 입력해주세요.")
  private String code;

  @ApiModelProperty(name="평가자 부서", example = "개발부")
  @NotBlank(message = "평가자 부서를 입력해주세요.")
  @Size(max = 45, message = "평가자 부서는 45자 이내로 입력해주세요.")
  private String department;

  @ApiModelProperty(name="평가자 직급", example = "사원")
  @Size(max = 30, message = "평가자 직급은 30자 이내로 입력해주세요.")
  private String position;

  @ApiModelProperty(name="평가자 전화번호", example = "010-1111-2222")
  @NotBlank(message = "평가자 전화번호를 입력해주세요.")
  @Size(max = 13, message = "평가자 전화번호는 13자 이내로 입력해주세요.")
  @Pattern(message = "전화번호 형식을 준수해주세요.", regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$")
  private String tel;

  @ApiModelProperty(name="평가자 이메일", example = "aaaa@bbb.com")
  @NotBlank(message = "평가자 이메일을 입력해주세요.")
  @Email(message = "이메일 형식을 준수해주세요.", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
  private String email;

  @ApiModelProperty(name="평가자 사진", example = "사진 url")
  private String image;

}
