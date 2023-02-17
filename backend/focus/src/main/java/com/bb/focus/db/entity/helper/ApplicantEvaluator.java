package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.evaluation.EvaluationResult;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "applicants_evaluators")
public class ApplicantEvaluator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "applicant_evaluator_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="evaluator_id")
  private Evaluator evaluator;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="applicant_id")
  private Applicant applicant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="interview_id")
  private Interview interview;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="evaluation_sheet_id")
  private EvaluationSheet evaluationSheet;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interview_room_id")
  private InterviewRoom interviewRoom;

  @Column(length = 1000)
  private String memo;
  @NotNull
  private int score;

  @OneToMany(targetEntity = com.bb.focus.db.entity.evaluation.EvaluationResult.class, mappedBy = "applicantEvaluator")
  private List<EvaluationResult> evaluationResultList = new ArrayList<>();

  public boolean addEvaluationResult(EvaluationResult evaluationResult){
    this.evaluationResultList.add(evaluationResult);
    if(evaluationResult.getApplicantEvaluator() != this){
      evaluationResult.setApplicantEvaluator(this);
    }
    return true;
  }

}