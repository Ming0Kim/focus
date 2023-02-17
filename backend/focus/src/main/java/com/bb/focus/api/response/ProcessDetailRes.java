package com.bb.focus.api.response;

import com.bb.focus.db.entity.interview.Interview;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProcessDetailRes")
@NoArgsConstructor
public class ProcessDetailRes extends ProcessRes{

  @ApiModelProperty(name = "면접(n차들) 목록")
  private List<InterviewRes> interviewList = new ArrayList<>();

  public ProcessDetailRes(Long id, String name, LocalDateTime startDate,
      LocalDateTime endDate, Byte interviewCount, Byte currentStep) {
    super(id, name, startDate, endDate, interviewCount, currentStep);
  }

}
