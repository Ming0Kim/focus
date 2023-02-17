package com.bb.focus.db.entity.interview;

import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.helper.InterviewEvaluator;
import com.bb.focus.db.entity.helper.InteviewApplicantPassLog;
import com.bb.focus.db.entity.process.Process;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "interviews")
@NoArgsConstructor
public class Interview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "interview_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "evaluation_sheet_id")
  private EvaluationSheet evaluationSheet;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "process_id")
  private Process process;

  @NotNull
  private Byte step;

  @NotNull
  @Column(length = 64)
  private String name;

  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ApplicantEvaluator.class, mappedBy = "interview")
  private List<ApplicantEvaluator> applicantEvaluatorList = new ArrayList<>();

  @OneToMany(mappedBy = "interview")
  private List<InteviewApplicantPassLog> InteviewApplicantPassLogList = new ArrayList<>();

  @OneToMany(targetEntity = com.bb.focus.db.entity.helper.InterviewEvaluator.class, mappedBy = "interview")
  private List<InterviewEvaluator> interviewEvaluatorList = new ArrayList<>();

  @OneToMany(targetEntity = com.bb.focus.db.entity.helper.InteviewApplicantPassLog.class, mappedBy = "interview")
  private List<InteviewApplicantPassLog> inteviewApplicantPassLogList = new ArrayList<>();

  @OneToMany(targetEntity = com.bb.focus.db.entity.interview.InterviewRoom.class, mappedBy = "interview")
  private List<InterviewRoom> interviewRoomList = new ArrayList<>();

  //연관관계 메서드
  public void setProcess(Process process) {
    if (this.process != null) {
      this.process.getInterviewList().remove(this);
    }
    this.process = process;
    process.getInterviewList().add(this);
  }

  public void setApplicantEvaluator(ApplicantEvaluator applicantEvaluator) {
    this.applicantEvaluatorList.add(applicantEvaluator);
    if (applicantEvaluator.getInterview() == null) {
      applicantEvaluator.setInterview(this);
    }
  }
}
