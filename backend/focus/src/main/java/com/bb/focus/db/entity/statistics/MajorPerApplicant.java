package com.bb.focus.db.entity.statistics;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MajorPerApplicant {

  @Id
  @GeneratedValue()
  @Column(name ="major_per_applicant_id")
  private long Id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="statistic_id")
  private  ApplicantStatistic applicantStatistic;

  @NotNull
  @Column(length = 200)
  private String majorName;

  @NotNull
  @ColumnDefault("0")
  private int applicantCount;

}
