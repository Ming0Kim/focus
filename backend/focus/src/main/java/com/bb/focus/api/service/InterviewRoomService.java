package com.bb.focus.api.service;

import com.bb.focus.api.request.InterviewRoomReq;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.interview.InterviewRoom;

import java.util.List;
import java.util.Optional;

public interface InterviewRoomService {

  //면접 일정 생성
  public InterviewRoom createInterviewRoom(InterviewRoomReq interviewRoomReq);

  //면접 일정 수정(기본 정보 수정)
  public Long updateInterviewRoom(Long interviewRoomId, InterviewRoomReq interviewRoomReq);

  //면접 일정 삭제
  public void removeInterviewRoom(Long interviewRoomId);

  //면접에 평가자 등록
  public void addEvaluatorToInterviewRoom(Long interviewRoomId, Long evaluatorId);

  //면접에 지원자 등록
  public void addApplicantToInterviewRoom(Long interviewRoomId, Long applicantId);

  //면접에서 평가자 삭제
  public void removeEvaluator(Long interviewRoomId, Long evaluatorId);

  //면접에서 지원자 삭제
  public void removeApplicant(Long interviewRoomId, Long applicantId);

  //면접 일정 조회(프로세스 -> 면접(N차) 별)
  public List<InterviewRoomRes> findAllInterviewRoom(Long interviewId);

  //면접 일정 별 평가자 리스트 조회
  public List<EvaluatorRes> findEvaluators(Long interviewRoomId);

  //면접 일정 별 지원자 리스트 조회
  public List<ApplicantRes> findApplicants(Long interviewRoomId);

  Optional<InterviewRoom> findById(Long id);

  public List<InterviewRoomRes> findUpToByEvaluator(Long evaluatorId);

  public List<InterviewRoomRes> findUpToByApplicant(Long applicantId);

  public List<InterviewRoomRes> findPastByEvaluator(Long evaluatorId);
}
