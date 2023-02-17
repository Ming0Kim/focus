package com.bb.focus.api.response;

import com.bb.focus.db.entity.applicant.Applicant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ApplicantDecisionRes")
@NoArgsConstructor
public class ApplicantDecisionRes {
  @ApiModelProperty(name="지원자 시퀀스 넘버", example = "1")
  private Long id;

  @ApiModelProperty(name="지원자 이름", example = "김싸피")
  private String name;

  @ApiModelProperty(name="지원자 수험번호", example = "123456")
  private String code;

  @ApiModelProperty(name="각 전형들 점수", example = "직무검사:57")
  private Map<String,Double> scoreList = new HashMap<>();

  @ApiModelProperty(name="최종학력", example = "대학원 박사")
  private String LastDegree;
  public ApplicantDecisionRes(Applicant applicant){
    id = applicant.getId();
    name = applicant.getName();
    code = applicant.getCode();
    LastDegree = ConvertDegree(applicant.getDegree());
  }
  public String ConvertDegree(String val){
    if(val.equals("j")) {
      return "전문 학사";
    }else if(val.equals("b")){
      return "학사";
    }else if(val.equals("m")){
      return "석사";
    }else if(val.equals("d")){
      return "박사";
    }else{
      return "Unknown";
    }
  }
}
