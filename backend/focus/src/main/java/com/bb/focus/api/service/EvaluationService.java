package com.bb.focus.api.service;

import com.bb.focus.api.request.DecisionReq;
import com.bb.focus.api.request.EvaluationResultReq;
import com.bb.focus.api.request.EvaluationResultUpdateReq;
import com.bb.focus.api.request.InterviewResultReq;
import com.bb.focus.api.response.ApplicantDecisionRes;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.request.EvaluationItemInfoReq;
import com.bb.focus.api.response.EvaluationSheetResultRes;
import com.bb.focus.db.entity.applicant.Status;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.interview.InterviewRoom;

import java.util.List;
import org.springframework.stereotype.Service;

public interface EvaluationService {


  boolean LoggingUserPass( List<DecisionReq> decisionReqList);
  boolean ApplicantEvaluation(EvaluationItemInfoReq result, Long ApplicantEvaluatorId, Long evaluationItemId);

  List<ApplicantDecisionRes> findApplicantDecisionByPass(Long processId);
  List<ApplicantRes> findApplicantByPass(Long processId);
  List<EvaluationSheetResultRes> findApplicantEvaluation(Long evaluatorId,Long applicantId,Long interviewId);
  boolean ModifyApplicantEvaluation(EvaluationResultUpdateReq evaluationResultUpdateReq);
  boolean UpdateApplicantEvaluationScore(Long ApplicantEvaluatorId);
  boolean UpdateApplicantEvaluationMemo(Long EvaluatorId,String memo);
  boolean createApplicantEvaluator(Long interviewId, InterviewRoom InterviewRoom, Long EvaluatorId,Long ApplicantId); // 평가지 시퀀스 넘버는

  List<ApplicantRes> findAttendingApplicants(Long interviewRoomId);
}
