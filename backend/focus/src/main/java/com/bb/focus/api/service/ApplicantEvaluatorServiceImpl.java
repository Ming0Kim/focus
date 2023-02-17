package com.bb.focus.api.service;

import com.bb.focus.api.request.AddRemoveApplicantReq;
import com.bb.focus.api.request.AddRemoveEvaluatorReq;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.repository.ApplicantEvaluatorRepository;
import com.bb.focus.db.repository.ApplicantRepository;
import com.bb.focus.db.repository.EvaluatorRepository;
import com.bb.focus.db.repository.InterviewRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantEvaluatorServiceImpl implements ApplicantEvaluatorService {

  private final ApplicantEvaluatorRepository applicantEvaluatorRepository;
  private final InterviewRoomRepository interviewRoomRepository;
  private final EvaluatorRepository evaluatorRepository;
  private final ApplicantRepository applicantRepository;

  @Transactional
  public void mappingEvaluatorWithApplicants(AddRemoveEvaluatorReq addRemoveEvaluatorReq) {

    InterviewRoom interviewRoom = interviewRoomRepository
        .findById(addRemoveEvaluatorReq.getInterviewRoomId())
        .orElseThrow(IllegalArgumentException::new);

    Evaluator evaluator = evaluatorRepository
        .findById(addRemoveEvaluatorReq.getEvaluatorId())
        .orElseThrow(IllegalArgumentException::new);

    //interview : 면접(n차)
    Interview interview = interviewRoom.getInterview();

    //evaluationSheet : 평가지
    EvaluationSheet evaluationSheet = interview.getEvaluationSheet();

    //applicant
    List<Long> applicantIds = applicantEvaluatorRepository.findApplicantIds(addRemoveEvaluatorReq.getInterviewRoomId());
    for(Long applicantId : applicantIds){
      Applicant applicant = applicantRepository.findById(applicantId).orElseThrow(IllegalArgumentException::new);

      ApplicantEvaluator applicantEvaluator = new ApplicantEvaluator();
      applicantEvaluator.setEvaluator(evaluator);
      applicantEvaluator.setApplicant(applicant);
      applicantEvaluator.setInterview(interview);
      applicantEvaluator.setEvaluationSheet(evaluationSheet);
      applicantEvaluator.setInterviewRoom(interviewRoom);
      applicantEvaluatorRepository.save(applicantEvaluator);
      applicant.getApplicantEvaluatorList().add(applicantEvaluator);
    }
  }

  @Transactional
  public void mappingApplicantWithEvaluators(AddRemoveApplicantReq addRemoveApplicantReq) {

    InterviewRoom interviewRoom = interviewRoomRepository
        .findById(addRemoveApplicantReq.getInterviewRoomId())
        .orElseThrow(IllegalArgumentException::new);

    Applicant applicant = applicantRepository
        .findById(addRemoveApplicantReq.getApplicantId())
        .orElseThrow(IllegalArgumentException::new);

    //interview : 면접(n차)
    Interview interview = interviewRoom.getInterview();

    //evaluationSheet : 평가지
    EvaluationSheet evaluationSheet = interview.getEvaluationSheet();

    //evaluator
    List<Long> evaluatorIds = applicantEvaluatorRepository.findEvaluatorIds(addRemoveApplicantReq.getInterviewRoomId());
    for(Long evaluatorId : evaluatorIds){
      Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow(IllegalArgumentException::new);

      ApplicantEvaluator applicantEvaluator = new ApplicantEvaluator();
      applicantEvaluator.setEvaluator(evaluator);
      applicantEvaluator.setApplicant(applicant);
      applicantEvaluator.setInterview(interview);
      applicantEvaluator.setEvaluationSheet(evaluationSheet);
      applicantEvaluator.setInterviewRoom(interviewRoom);

      applicantEvaluatorRepository.save(applicantEvaluator);
    }
  }
}
