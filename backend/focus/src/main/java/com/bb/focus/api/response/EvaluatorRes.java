package com.bb.focus.api.response;

import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.evaluator.Evaluator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluatorRes")
@NoArgsConstructor
public class EvaluatorRes {

  @ApiModelProperty(name="평가자 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name="평가자 이름", example = "홍길동")
  private String name;

  @ApiModelProperty(name="평가자 사번", example = "1111")
  private String code;

  @ApiModelProperty(name="평가자 부서", example = "개발부")
  private String department;

  @ApiModelProperty(name="평가자 직급", example = "사원")
  private String position;

  @ApiModelProperty(name="평가자 사진", example = "사진 url")
  private String image;

  @ApiModelProperty(name="평가자 아이디", example = "xxxAxxxx")
  private String userId;

  @ApiModelProperty(name="평가자 전화번호", example = "010-1235-5456")
  private String tel;

  @ApiModelProperty(name="평가자 이메일", example = "abc@gmail.com")
  private String email;

  public EvaluatorRes(Evaluator evaluator) {
    id = evaluator.getId();
    name = evaluator.getName();
    code = evaluator.getCode();
    department = evaluator.getDepartment();
    position = evaluator.getPosition();
    image = evaluator.getImage();
  }

  public EvaluatorRes(Long id, String name, String code, String department, String position,
      String image, String userId, String tel, String email) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.department = department;
    this.position = position;
    this.image = image;
    this.userId = userId;
    this.tel = tel;
    this.email = email;
  }

  public static EvaluatorRes of(Evaluator user){
    EvaluatorRes res = new EvaluatorRes();
    res.setUserId(user.getUserId());
    res.setTel(user.getTel());
    res.setEmail(user.getEmail());
    res.setName(user.getName());
    return res;
  }
}
