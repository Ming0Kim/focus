package com.bb.focus.api.response;

import com.bb.focus.db.entity.evaluator.Evaluator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluatorDetailRes")
public class EvaluatorDetailRes {

  @ApiModelProperty(name="평가자 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name="평가자 아이디", example = "xxExx")
  private String userId;

  @ApiModelProperty(name="평가자 이름", example = "홍길동")
  private String name;

  @ApiModelProperty(name="평가자 사번", example = "1111")
  private String code;

  @ApiModelProperty(name="평가자 부서", example = "개발부")
  private String department;

  @ApiModelProperty(name="평가자 직급", example = "사원")
  private String position;

  @ApiModelProperty(name="평가자 전화번호", example = "010-0000-0000")
  private String tel;

  @ApiModelProperty(name="평가자 이메일", example = "xxx@xxx.xxx")
  private String email;

  @ApiModelProperty(name="평가자 계정 만료일", example = "2024-01-02 13:22:22.000000")
  private LocalDateTime expireDate;

  @ApiModelProperty(name="평가자 사진", example = "사진 url")
  private String image;

  public EvaluatorDetailRes(Evaluator evaluator) {
    id = evaluator.getId();
    userId = evaluator.getUserId();
    name = evaluator.getName();
    code = evaluator.getCode();
    department = evaluator.getDepartment();
    position = evaluator.getPosition();
    tel = evaluator.getTel();
    email = evaluator.getEmail();
    expireDate = evaluator.getExpireDate();
    image = evaluator.getImage();
  }
}
