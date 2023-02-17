package com.bb.focus.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticRes {
  private int avgApplicantAge;
  private int femaleCount;
  private int maleCount;
  private int avgAwardCount;
  private int avgAcitivityCount;
  private int graduateCount;
  private int universityCount;
  private int collegeCount;
}
