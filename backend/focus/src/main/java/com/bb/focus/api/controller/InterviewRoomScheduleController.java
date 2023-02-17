package com.bb.focus.api.controller;

import com.bb.focus.api.request.AddRemoveApplicantReq;
import com.bb.focus.api.request.AddRemoveEvaluatorReq;
import com.bb.focus.api.request.InterviewRoomReq;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomInfoRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.api.service.ApplicantEvaluatorService;
import com.bb.focus.api.service.EvaluationService;
import com.bb.focus.api.service.InterviewRoomService;
import com.bb.focus.api.service.InterviewService;
import com.bb.focus.api.service.ProcessEvaluatorService;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.db.entity.interview.InterviewRoom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "면접실 : 면접 일정 API", tags = {"InterviewRoom Schedule"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/interview/schedule")
public class InterviewRoomScheduleController {

  private final InterviewRoomService interviewRoomService;
  private final EvaluationService evaluationService;
  private final ProcessEvaluatorService processEvaluatorService;
  private final ApplicantEvaluatorService applicantEvaluatorService;
  private final InterviewService interviewService;

  @ApiOperation(value = "면접 일정 생성")
  @PostMapping("/{process-id}")
  public ResponseEntity<?> createInterviewRoom(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "process-id") Long processId,
      @RequestBody @Valid @ApiParam(value = "면접 일정 생성 정보", required = true) InterviewRoomReq interviewRoomReq) {

    InterviewRoom interviewRoom = interviewRoomService.createInterviewRoom(interviewRoomReq);

    Long interviewId = interviewRoomReq.getInterviewId();
    Long[] evaluatorIds = interviewRoomReq.getEvaluators();
    Long[] applicantIds = interviewRoomReq.getApplicants();

    //지원자-평가자 테이블에 데이터 넣기
    for(int e = 0, elen = evaluatorIds.length; e < elen; e++) {
      for (int a = 0, alen = applicantIds.length; a < alen; a++) {
        evaluationService.createApplicantEvaluator(interviewId, interviewRoom, evaluatorIds[e], applicantIds[a]);
      }
    }

    //프로세스 - 평가자 테이블에 데이터 넣기
    for(int e = 0, elen = evaluatorIds.length; e < elen; e++) {
      processEvaluatorService.saveProcessEvaluator(processId, evaluatorIds[e]);
    }

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "면접 일정 수정")
  @PutMapping("/{interview-room-id}")
  public ResponseEntity<Map<String, Long>> updateInterviewRoom(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "interview-room-id") Long interviewRoomId,
      @RequestBody @Valid @ApiParam(value = "면접 일정 수정 정보", required = true) InterviewRoomReq interviewRoomReq) {

    Long id = interviewRoomService.updateInterviewRoom(interviewRoomId, interviewRoomReq);

    Map<String, Long> result = new HashMap<>();
    result.put("id", id);

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "면접 일정 삭제")
  @DeleteMapping("/{interview-room-id}")
  public ResponseEntity<?> removeInterviewRoom(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "interview-room-id") Long interviewRoomId) {

    interviewRoomService.removeInterviewRoom(interviewRoomId);
    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "면접 일정에 단일 평가자 추가")
  @PostMapping("/add/evaluator")
  public ResponseEntity<?> addEvaluator(
      @ApiIgnore Authentication authentication,
      @RequestBody @Valid @ApiParam(value = "평가자 추가 요청 정보") AddRemoveEvaluatorReq addEvaluatorReq) {

    interviewRoomService.addEvaluatorToInterviewRoom(addEvaluatorReq.getInterviewRoomId(),
        addEvaluatorReq.getEvaluatorId());

    processEvaluatorService.saveProcessEvaluator(addEvaluatorReq.getProcessId(),
        addEvaluatorReq.getEvaluatorId());

    applicantEvaluatorService.mappingEvaluatorWithApplicants(addEvaluatorReq);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "면접 일정에 단일 지원자 추가")
  @PostMapping("/add/applicant")
  public ResponseEntity<?> addApplicant(
      @ApiIgnore Authentication authentication,
      @RequestBody @Valid @ApiParam(value = "지원자 추가 요청 정보") AddRemoveApplicantReq addApplicantReq) {

    interviewRoomService.addApplicantToInterviewRoom(addApplicantReq.getInterviewRoomId(),
        addApplicantReq.getApplicantId());

    applicantEvaluatorService.mappingApplicantWithEvaluators(addApplicantReq);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "면접 일정에서 단일 평가자 삭제")
  @DeleteMapping("/remove/evaluator")
  public ResponseEntity<?> removeEvaluator(
      @ApiIgnore Authentication authentication,
      @RequestBody @Valid @ApiParam(value = "평가자 삭제 요청 정보") AddRemoveEvaluatorReq removeEvaluatorReq) {

    interviewRoomService.removeEvaluator(removeEvaluatorReq.getInterviewRoomId(),
        removeEvaluatorReq.getEvaluatorId());

    processEvaluatorService.removeProcessEvaluator(removeEvaluatorReq.getProcessId(),
        removeEvaluatorReq.getEvaluatorId());

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "면접 일정에서 단일 지원자 삭제")
  @DeleteMapping("/remove/applicant")
  public ResponseEntity<?> removeApplicant(
      @ApiIgnore Authentication authentication,
      @RequestBody @Valid @ApiParam(value = "지원자 삭제 요청 정보") AddRemoveApplicantReq removeApplicantReq) {

    interviewRoomService.removeApplicant(removeApplicantReq.getInterviewRoomId(),
        removeApplicantReq.getApplicantId());

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "면접 별 전체 면접 일정 조회")
  @GetMapping("/{interview-id}")
  public ResponseEntity<List<InterviewRoomRes>> getInterviewSchedule(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "interview-id") Long interviewId) {

    List<InterviewRoomRes> result = interviewRoomService.findAllInterviewRoom(interviewId);

    return ResponseEntity.status(200).body(result);
  }


  @ApiOperation(value = "특정 면접 일정 조회")
  @GetMapping("/detail/{interview-room-id}")
  public ResponseEntity<InterviewRoomRes> getInterviewScheduleInfo(
      @PathVariable(value = "interview-room-id") Long interviewRoomId) {

    InterviewRoomRes result = new InterviewRoomRes(interviewRoomService.findById(interviewRoomId).get());

    return ResponseEntity.status(200).body(result);
  }


  @ApiOperation(value = "면접 별 평가자 리스트 조회")
  @GetMapping("/{interview-room-id}/evaluators")
  public ResponseEntity<List<EvaluatorRes>> getEvaluators(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "interview-room-id") Long interviewRoomId) {

    List<EvaluatorRes> result = interviewRoomService.findEvaluators(interviewRoomId);

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "면접 별 지원자 리스트 조회")
  @GetMapping("/{interview-room-id}/applicants")
  public ResponseEntity<List<ApplicantRes>> getApplicants(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "interview-room-id") Long interviewRoomId) {

    List<ApplicantRes> result = interviewRoomService.findApplicants(interviewRoomId);

    return ResponseEntity.status(200).body(result);
  }

  /**
   * request : processId
   * response : interviewId List
   */
  @ApiOperation(value = "프로세스 id로 interview Id 리스트 얻기")
  @GetMapping("/interview/ids/{process-id}")
  public ResponseEntity<?> getInterviewIdList(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "process-id") Long processId) {

    List<InterviewRoomInfoRes> result = interviewService.findInterviewRoomIds(processId);

    return ResponseEntity.status(200).body(result);

  }
}
