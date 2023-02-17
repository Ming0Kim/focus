package com.bb.focus.api.response;

import com.bb.focus.db.entity.interview.Interview;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRes {

  private Long id;
  private Byte step;
  private String name;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Long evaluationSheetId;

  public InterviewRes(Interview interview) {
    id = interview.getId();
    step = interview.getStep();
    name = interview.getName();
    startDate = interview.getStartDate();
    endDate = interview.getEndDate();
    evaluationSheetId = interview.getEvaluationSheet().getId();
  }
}
