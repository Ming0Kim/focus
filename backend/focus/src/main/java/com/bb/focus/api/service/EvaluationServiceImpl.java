package com.bb.focus.api.service;

import com.bb.focus.api.request.DecisionReq;
import com.bb.focus.api.request.EvaluationItemInfoReq;
import com.bb.focus.api.request.EvaluationResultUpdateReq;
import com.bb.focus.api.request.InterviewResultReq;
import com.bb.focus.api.response.ApplicantDecisionRes;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.EvaluationSheetResultRes;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import com.bb.focus.db.entity.applicant.Status;
import com.bb.focus.db.entity.evaluation.EvaluationItem;
import com.bb.focus.db.entity.evaluation.EvaluationResult;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.repository.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService{
  private final ApplicantPassLogRepository applicantPassLogRepo;
  private final ApplicantRepository applicantRepo;
  private final ProcessRepository processRepo;
  private final ApplicantEvaluatorRepository applicantEvaluatorRepo;
  private final EvaluationSheetItemRepository evaluationSheetItemRepo;
  private final EvaluationSheetRepository evaluationSheetRepo;
  private final EvaluationResultRepository evaluationResultRepo;
  private final InterviewRepository interviewRepo;
  private final EvaluatorRepository evaluatorRepo;


  // 면접평가 시에 해당 데이터를 저장하는 함수
  public boolean ApplicantEvaluation(EvaluationItemInfoReq result, Long applicantEvaluatorId, Long evaluationItemId){
    ApplicantEvaluator applicantEvaluator=applicantEvaluatorRepo.findById(applicantEvaluatorId).orElseThrow(IllegalAccessError::new);
    List<EvaluationResult> evaluationResultList=applicantEvaluator.getEvaluationResultList();

    if(evaluationResultList != null) {
      for (EvaluationResult evaluationResult : evaluationResultList) {
        if (evaluationResult.getEvaluationItem().getId() == evaluationItemId) {
          evaluationResult.setContent(result.getContent());
          evaluationResult.setScore(result.getScore());
          return true;
        }
      }
    }

    EvaluationItem evaluationItem = evaluationSheetItemRepo.findById(evaluationItemId).orElseThrow(IllegalAccessError::new);
    EvaluationResult evaluationResult = new EvaluationResult();
    evaluationResult.setContent(result.getContent());
    evaluationResult.setScore(result.getScore());
    applicantEvaluator.addEvaluationResult(evaluationResult);
    evaluationResult.setEvaluationItem(evaluationItem);
    evaluationResultRepo.save(evaluationResult);

    return true;
  }


  @Override
  public boolean UpdateApplicantEvaluationMemo(Long applicantEvaluatorId,String memo) {
    ApplicantEvaluator applicantEvaluator=applicantEvaluatorRepo.findById(applicantEvaluatorId).orElseThrow(IllegalAccessError::new);
    applicantEvaluator.setMemo(memo);
    return true;
  }

  // 면접 합격 결과를 로그로 찍는 함수. 결과에 들어갈 필요가 있음.
  public boolean LoggingUserPass(List<DecisionReq> decisionReqList){
    Long processId = decisionReqList.get(0).getProcessId();
    // 로그 생성 후 -> process -> applicant -> interview를 통하여 각자 데이터 추출 이 때 status가 p라면 applicant의 현재 합격 여부 +1;
    Process process =processRepo.findById(processId).orElseThrow(IllegalAccessError::new);

    for(DecisionReq decisionReq:decisionReqList){

//      System.out.println("for문 시작........................................................");

      ApplicantPassLog applicantPassLog= new ApplicantPassLog();
      Applicant applicant=applicantRepo.findById(decisionReq.getInterviewResultReq().getApplicantId()).orElseThrow(IllegalAccessError::new);
      applicant.addApplicantPasslog(applicantPassLog);

//      System.out.println("지원자 이름....................................: "+applicant.getName());

      if(!applicantPassLog.setApplicantData(applicant)){
//        System.out.println("check1.................................: "+applicantPassLog.setApplicantData(applicant));
        return false;
      }
      if(!applicantPassLog.setProcess(process)){
//        System.out.println("check2.................................: "+applicantPassLog.setProcess(process));
        return false;
      }

      List<Interview> interviewList =process.getInterviewList();

      for(Interview interview :interviewList){
        if(interview.getStep() == process.getCurrentStep()){
          applicantPassLog.setInterviewName(interview.getName());
          break;
        }
      }
      Status status = Status.valueOf(decisionReq.getInterviewResultReq().getPass());
      if(Status.valueOf(decisionReq.getInterviewResultReq().getPass()) == Status.P){
        byte cur_pass= (byte)(applicant.getPass()+1);
        applicant.setPass(cur_pass);
      }
      applicantPassLog.setStatus(status);
      applicantPassLog.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

      // 총점 구하기 위해서 applicantEvaluator에 접근
      double total = 0; int loop = 0;
      List<ApplicantEvaluator> applicantEvaluatorList=applicant.getApplicantEvaluatorList();

      for(ApplicantEvaluator applicantEvaluator:applicantEvaluatorList) {
//        System.out.println("loop: " + loop + ", step1: "+ applicantEvaluator.getInterview().getStep() + ", step2: " + process.getCurrentStep());
        if (applicantEvaluator.getInterview().getStep() == process.getCurrentStep()) {
          total += applicantEvaluator.getScore();
          loop++;
        }
      }

//      System.out.println("loop......................................: "+loop);

      if(total != 0 && loop != 0){
        applicantPassLog.setScore(total / loop);
      } else applicantPassLog.setScore(0);

      applicantPassLogRepo.save(applicantPassLog);
    }

    byte CurStep = (byte)(process.getCurrentStep()+1);
    process.setCurrentStep(CurStep);
    return true;
  }


  public List<EvaluationSheetResultRes> findApplicantEvaluation(Long evaluatorId,Long applicantId,Long interviewId){
    ApplicantEvaluator applicantEvaluator = applicantEvaluatorRepo.findByEvaluatorIdAndApplicantIdAndInterviewId(evaluatorId,applicantId,interviewId);
    List<EvaluationSheetResultRes> evaluationSheetResultResList = new ArrayList<>();
    for(EvaluationResult evaluationResult:applicantEvaluator.getEvaluationResultList()){
          EvaluationSheetResultRes evaluationResultRes = new EvaluationSheetResultRes(evaluationResult);
          evaluationSheetResultResList.add(evaluationResultRes);
    }


    return evaluationSheetResultResList;
  }
  public boolean ModifyApplicantEvaluation(EvaluationResultUpdateReq evaluationResultUpdateReq){
    EvaluationResult evaluationResult =evaluationResultRepo.findById(evaluationResultUpdateReq.getEvaluationResultId()).orElseThrow(IllegalAccessError::new);

    if(evaluationResult == null) {
      return false;
    }
    evaluationResult.setContent(evaluationResultUpdateReq.getContent());
    evaluationResult.setScore(evaluationResultUpdateReq.getScore());

    return true;

  }
  public List<ApplicantDecisionRes> findApplicantDecisionByPass(Long processId){
    Process process = processRepo.findById(processId).orElseThrow(IllegalAccessError::new);
    byte cur_step = (byte)(process.getCurrentStep()-1);
    List<Applicant> applicantList= applicantRepo.findAllByProcessIdAndPass(processId,cur_step);
    List<ApplicantDecisionRes> applicantDecisionResList = new ArrayList<>();
    for(Applicant applicant :applicantList){
      ApplicantDecisionRes applicantDecisionRes = new ApplicantDecisionRes(applicant);
      for(ApplicantPassLog applicantPassLog:applicant.getApplicantPassLogList()){
        applicantDecisionRes.getScoreList().put(applicantPassLog.getProcessName(),applicantPassLog.getScore());
      }
      applicantDecisionResList.add(applicantDecisionRes);
    }
    return applicantDecisionResList;
  }
  public List<ApplicantRes> findApplicantByPass(Long processId){
    Process process = processRepo.findById(processId).orElseThrow(IllegalAccessError::new);
    byte cur_step = (byte)(process.getCurrentStep()-1);
    List<Applicant> applicantList= applicantRepo.findAllByProcessIdAndPass(processId,cur_step);

    List<ApplicantRes> applicantResList = new ArrayList<>();
    for(Applicant applicant:applicantList){
      ApplicantRes applicantRes = new ApplicantRes(applicant);
      applicantResList.add(applicantRes);
    }
    return applicantResList;
  }
  public boolean createApplicantEvaluator(Long interviewId,InterviewRoom interviewRoom, Long evaluatorId,Long applicantId){
    ApplicantEvaluator applicantEvaluator = new ApplicantEvaluator();

    Interview interview= interviewRepo.findInterviewById(interviewId);
    Evaluator evaluator = evaluatorRepo.findEvaluatorById(evaluatorId);
    Applicant applicant = applicantRepo.findApplicantById(applicantId);

    EvaluationSheet evaluationSheet = evaluationSheetRepo.findById(interview.getEvaluationSheet().getId()).orElseThrow(IllegalAccessError::new);
    interview.setApplicantEvaluator(applicantEvaluator);
    evaluator.setApplicantEvaluator(applicantEvaluator);
    applicantEvaluator.setApplicant(applicant);
    applicantEvaluator.setEvaluationSheet(evaluationSheet);
    applicantEvaluator.setInterviewRoom(interviewRoom);

    applicantEvaluatorRepo.save(applicantEvaluator);
    return true;
  }

  //면접실에 참여하는 지원자 정보 리스트 조회
  public List<ApplicantRes> findAttendingApplicants(Long interviewRoomId) {
    List<ApplicantRes> result = applicantEvaluatorRepo.findAttendingApplicants(interviewRoomId);
    return result;
  }

  // 총점 재계산하는 함수
  public boolean UpdateApplicantEvaluationScore(Long ApplicantEvaluatorId){
    List<EvaluationResult> evaluationResultList=evaluationResultRepo.findByapplicantEvaluatorId(ApplicantEvaluatorId);
    int total =0;
    for(EvaluationResult evaluationResult:evaluationResultList){
        total +=evaluationResult.getScore();
    }
    ApplicantEvaluator applicantEvaluator=applicantEvaluatorRepo.findById(ApplicantEvaluatorId).orElseThrow(IllegalAccessError::new);
    applicantEvaluator.setScore(total);
    applicantEvaluatorRepo.save(applicantEvaluator);
    return true;
  }


}
