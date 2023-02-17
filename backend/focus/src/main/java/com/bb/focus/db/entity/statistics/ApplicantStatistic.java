package com.bb.focus.db.entity.statistics;


import com.bb.focus.db.entity.process.Process;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="applicant_statistic")
public class ApplicantStatistic {

  @Id
  @GeneratedValue()
  @Column(name="statistic_id")
  private Long id;

  @OneToOne(fetch =  FetchType.LAZY)
  @JoinColumn(name="process_id")
  private Process processId;

  @NotNull
  @ColumnDefault("0")
  private int avgApplicantAge;

  @NotNull
  @ColumnDefault("0")
  private int femaleCount;

  @NotNull
  @ColumnDefault("0")
  private int maleCount;

  @NotNull
  @ColumnDefault("0")
  private int avgAwardCount;

  @NotNull
  @ColumnDefault("0")
  private int avgAcitivityCount;

  @NotNull
  @ColumnDefault("0")
  private int graduateCount;

  @NotNull
  @ColumnDefault("0")
  private int universityCount;

  @NotNull
  @ColumnDefault("0")
  private int collegeCount;

  @OneToMany(targetEntity = com.bb.focus.db.entity.statistics.MajorPerApplicant.class ,mappedBy = "applicantStatistic")
  private List<MajorPerApplicant> majorPerApplicantList = new ArrayList<>();
}
