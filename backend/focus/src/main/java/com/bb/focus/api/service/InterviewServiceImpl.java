package com.bb.focus.api.service;

import com.bb.focus.api.request.InterviewReq;
import com.bb.focus.api.response.InterviewRoomInfoRes;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.repository.EvaluationSheetRepository;
import com.bb.focus.db.repository.InterviewRepository;
import com.bb.focus.db.repository.ProcessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewServiceImpl implements InterviewService {

  private final ProcessRepository processRepository;
  private final InterviewRepository interviewRepository;
  private final EvaluationSheetRepository evaluationSheetRepository;

  @Transactional
  public void createInterview(Long processId, InterviewReq interviewReq) {
    //processId 매핑, interviewReq에서 각종 정보와 evaluation sheet 매핑

    Process process = processRepository.findById(processId)
        .orElseThrow(IllegalArgumentException::new);
    EvaluationSheet evaluationSheet = evaluationSheetRepository.findById(
        interviewReq.getEvaluationSheetId()).orElseThrow(IllegalArgumentException::new);

    Interview interview = new Interview();
    interview.setProcess(process);
    interview.setEvaluationSheet(evaluationSheet);
    interview.setStep(interviewReq.getStep());
    interview.setName(interviewReq.getName());
    interview.setStartDate(interviewReq.getStartDate());
    interview.setEndDate(interviewReq.getEndDate());

    interviewRepository.save(interview);
  }

  @Override
  public List<InterviewRoomInfoRes> findInterviewRoomIds(Long processId) {

    List<InterviewRoomInfoRes> interviewRoomIdList = interviewRepository.findInterviewRoomIds(processId);
    return interviewRoomIdList;
  }

  @Override
  public Interview findInterviewById(Long interviewId) {
    Interview interview = interviewRepository.findInterviewById(interviewId);
    return interview;
  }
}
