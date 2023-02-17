package com.bb.focus.api.service;

import com.bb.focus.api.request.InterviewRoomReq;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.helper.ApplicantInterviewRoom;
import com.bb.focus.db.entity.helper.EvaluatorInterviewRoom;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.entity.interview.Room;
import com.bb.focus.db.repository.ApplicantEvaluatorRepository;
import com.bb.focus.db.repository.ApplicantInterviewRoomRepository;
import com.bb.focus.db.repository.ApplicantRepository;
import com.bb.focus.db.repository.EvaluatorInterviewRoomRepository;
import com.bb.focus.db.repository.EvaluatorRepository;
import com.bb.focus.db.repository.InterviewRepository;
import com.bb.focus.db.repository.InterviewRoomRepository;
import java.time.Duration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewRoomServiceImpl implements InterviewRoomService {

  private final InterviewRoomRepository interviewRoomRepository;
  private final EvaluatorInterviewRoomRepository evaluatorInterviewRoomRepository;
  private final ApplicantInterviewRoomRepository applicantInterviewRoomRepository;
  private final InterviewRepository interviewRepository;
  private final EvaluatorRepository evaluatorRepository;
  private final ApplicantRepository applicantRepository;
  private final ApplicantEvaluatorRepository applicantEvaluatorRepository;
  private final RoomService roomService;

  @Transactional
  public InterviewRoom createInterviewRoom(InterviewRoomReq interviewRoomReq) {

    InterviewRoom interviewRoom = new InterviewRoom();

    //기본정보 setting
    interviewRoom.setName(interviewRoomReq.getName());
    interviewRoom.setStartTime(interviewRoomReq.getStartTime());
    interviewRoom.setEndTime(interviewRoomReq.getEndTime());
    interviewRoom.setDate(interviewRoomReq.getStartTime().toLocalDate());

    Duration duration = Duration.between(interviewRoom.getStartTime(), interviewRoom.getEndTime());
    int interviewDuration = (int)(duration.getSeconds() / 60);

    interviewRoom.setDuration(interviewDuration);

    //외래키(면접 id) setting
    Long interviewId = interviewRoomReq.getInterviewId();
    Interview interview = interviewRepository.findById(interviewId).orElseThrow(IllegalArgumentException::new);
    interviewRoom.setInterview(interview);
    interviewRoom.setInterviewRound(interview.getStep());   //면접 차수 넣기

    //평가자 리스트 setting
    Long[] evaluatorIds = interviewRoomReq.getEvaluators();
    for(Long evaluatorId : evaluatorIds){
      EvaluatorInterviewRoom evaluatorInterviewRoom = new EvaluatorInterviewRoom();
      Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow(IllegalArgumentException::new);
      evaluatorInterviewRoom.setEvaluator(evaluator);
      evaluatorInterviewRoom.setInterviewRoom(interviewRoom);
      evaluatorInterviewRoomRepository.save(evaluatorInterviewRoom);
    }

    //지원자 리스트 setting
    Long[] applicantIds = interviewRoomReq.getApplicants();
    for(Long applicantId : applicantIds){
      ApplicantInterviewRoom applicantInterviewRoom = new ApplicantInterviewRoom();
      Applicant applicant = applicantRepository.findById(applicantId).orElseThrow(IllegalArgumentException::new);
      applicant.setExpireDate(interviewRoomReq.getEndTime().plusMinutes(30));   //계정 만료 시간 설정
      applicantInterviewRoom.setApplicant(applicant);
      applicantInterviewRoom.setInterviewRoom(interviewRoom);
      applicantInterviewRoomRepository.save(applicantInterviewRoom);
    }

    //룸 생성 & 외래키 setting
    Room room = roomService.autoCreateRoom(interviewId);
    roomService.updateRoomById(room);
    interviewRoom.setRoom(room);

    //프로세스명 넣기
    interviewRoom.setProcessName(interview.getProcess().getName());

    InterviewRoom savedEntity = interviewRoomRepository.save(interviewRoom);
    return savedEntity;
  }

  @Transactional
  public Long updateInterviewRoom(Long interviewRoomId, InterviewRoomReq interviewRoomReq) {

    InterviewRoom interviewRoom = interviewRoomRepository.findById(interviewRoomId).orElseThrow(IllegalArgumentException::new);

    interviewRoom.setName(interviewRoomReq.getName());
    interviewRoom.setStartTime(interviewRoomReq.getStartTime());
    interviewRoom.setEndTime(interviewRoomReq.getEndTime());
    interviewRoom.setDate(interviewRoomReq.getStartTime().toLocalDate());

    Duration duration = Duration.between(interviewRoom.getStartTime(), interviewRoom.getEndTime());
    int interviewDuration = (int)(duration.getSeconds() / 60);

    interviewRoom.setDuration(interviewDuration);

    return interviewRoom.getId();
  }

  @Transactional
  public void removeInterviewRoom(Long interviewRoomId) {
    interviewRoomRepository.deleteById(interviewRoomId);
  }

  @Transactional
  public void addEvaluatorToInterviewRoom(Long interviewRoomId, Long evaluatorId) {

    InterviewRoom interviewRoom = interviewRoomRepository.findById(interviewRoomId).orElseThrow(IllegalArgumentException::new);

    Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow(IllegalArgumentException::new);

    EvaluatorInterviewRoom evaluatorInterviewRoom = new EvaluatorInterviewRoom();
    evaluatorInterviewRoom.setInterviewRoom(interviewRoom);
    evaluatorInterviewRoom.setEvaluator(evaluator);

    evaluatorInterviewRoomRepository.save(evaluatorInterviewRoom);
  }

  @Transactional
  public void addApplicantToInterviewRoom(Long interviewRoomId, Long applicantId) {

    InterviewRoom interviewRoom = interviewRoomRepository.findById(interviewRoomId).orElseThrow(IllegalArgumentException::new);

    Applicant applicant = applicantRepository.findById(applicantId).orElseThrow(IllegalArgumentException::new);

    ApplicantInterviewRoom applicantInterviewRoom = new ApplicantInterviewRoom();
    applicantInterviewRoom.setInterviewRoom(interviewRoom);
    applicantInterviewRoom.setApplicant(applicant);

    applicantInterviewRoomRepository.save(applicantInterviewRoom);
  }

  @Transactional
  public void removeEvaluator(Long interviewRoomId, Long evaluatorId) {
    evaluatorInterviewRoomRepository.deleteByInterviewRoomIdAndEvaluatorId(interviewRoomId, evaluatorId);
    applicantEvaluatorRepository.deleteByInterviewRoomIdAndEvaluatorId(interviewRoomId, evaluatorId);
  }

  @Transactional
  public void removeApplicant(Long interviewRoomId, Long applicantId) {
    applicantInterviewRoomRepository.deleteByInterviewRoomIdAndApplicantId(interviewRoomId, applicantId);
    applicantEvaluatorRepository.deleteByInterviewRoomIdAndApplicantId(interviewRoomId, applicantId);
  }

  //면접 일정 조회(프로세스 -> 면접(N차) 별)
  public List<InterviewRoomRes> findAllInterviewRoom(Long interviewId) {

    List<InterviewRoom> interviewRooms = interviewRoomRepository.findByInterviewId(interviewId);
    List<InterviewRoomRes> results = interviewRooms.stream()
        .map(ir -> new InterviewRoomRes(
            ir.getId(),
            ir.getName(),
            ir.getStartTime(),
            ir.getEndTime(),
            ir.getDuration(),
            ir.getDate(),
            ir.getInterviewRound(),
            ir.getProcessName(),
                ir.getRoom().getRealCode()
        )).collect(Collectors.toList());

    return results;
  }

  //면접 일정 별 평가자 리스트 조회
  public List<EvaluatorRes> findEvaluators(Long interviewRoomId) {

    List<EvaluatorRes> evaluators = interviewRoomRepository.findEvaluatorsByInterviewId(interviewRoomId);
    return evaluators;
  }

  public List<ApplicantRes> findApplicants(Long interviewRoomId) {

    List<ApplicantRes> applicants = interviewRoomRepository.findApplicantsByInterviewId(interviewRoomId);
    return applicants;
  }

  //면접 일정 별 지원자 리스트 조회

  @Override
  public Optional<InterviewRoom> findById(Long id) {
        return interviewRoomRepository.findById(id);
  }

  @Override
  public List<InterviewRoomRes> findUpToByEvaluator(Long evaluatorId) {
    return interviewRoomRepository.findInterviewRoomByEvaluatorId(evaluatorId);
  }

  @Override
  public List<InterviewRoomRes> findUpToByApplicant(Long applicantId) {
    return interviewRoomRepository.findInterviewRoomByApplicantId(applicantId);
  }

  @Override
  public List<InterviewRoomRes> findPastByEvaluator(Long evaluatorId) {
    return interviewRoomRepository.findPastInterviewRoomByEvaluatorId(evaluatorId);
  }

}

