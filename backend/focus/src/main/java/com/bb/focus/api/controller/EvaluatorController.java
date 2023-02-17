package com.bb.focus.api.controller;

import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.api.service.EvaluatorService;
import com.bb.focus.api.service.InterviewRoomService;
import com.bb.focus.common.auth.FocusUserDetails;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.helper.ApplicantInterviewRoom;
import com.bb.focus.db.entity.helper.EvaluatorInterviewRoom;
import com.bb.focus.db.entity.interview.InterviewRoom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "평가자 API", tags = {"Evaluator"})
@RestController
@CrossOrigin("*")
@RequestMapping("/api/evaluators")
public class EvaluatorController {

  @Autowired
  EvaluatorService evaluatorService;

  @Autowired
  InterviewRoomService interviewRoomService;

  @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
  @GetMapping("/me")
  public ResponseEntity<EvaluatorRes> getUserInfo(@ApiIgnore Authentication authentication) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    Evaluator evaluator = evaluatorService.getEvaluatorById(id);

    return ResponseEntity.status(200).body(EvaluatorRes.of(evaluator));
  }

  @ApiOperation(value = "해당 평가자의 면접실 코드번호 조회")
  @GetMapping("/enter")
  public ResponseEntity<?> getRealRoomCode(
      @RequestBody @Valid @ApiParam(value = "면접 일정 시퀀스 넘버", required = true) Long evaluatorId) {

    return ResponseEntity.status(200).body(evaluatorId);
  }

  @ApiOperation(value = "날짜별로 해당 평가자에게 배정된 면접 리스트 조회")
  @GetMapping("/list")
  public ResponseEntity<?> getInterviewRoomsInfo(@ApiIgnore Authentication authentication) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    List<InterviewRoomRes> results = new ArrayList<>();
    // 평가자 아이디로 평가자 객체 가져오기
    Evaluator evaluator = evaluatorService.getEvaluatorById(id);
    // 해당 평가자에 저장돼있는 evaluator interview room list 가져오기
    List<EvaluatorInterviewRoom> evaluatorInterviewRoomList = evaluator.getEvaluatorInterviewRoomList();
    // 그 각각 evaluator interview room에서 interviewroom 정보 가져오기
    for (EvaluatorInterviewRoom eir : evaluatorInterviewRoomList) {
      InterviewRoom tmp = eir.getInterviewRoom();
      results.add(new InterviewRoomRes(eir.getInterviewRoom()));
    }
    Map<LocalDate, List<InterviewRoomRes>> ret = results.stream()
        .sorted()
        .collect(Collectors.groupingBy(InterviewRoomRes::getDate));

    return ResponseEntity.status(200).body(ret);
  }

  @ApiOperation(value = "해당 평가자에게 가장 가까운 면접 1개 정보 조회")
  @GetMapping("/next")
  public ResponseEntity<?> getNextInterviewRoomInfo(
      @ApiIgnore Authentication authentication) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    List<InterviewRoomRes> resultList = interviewRoomService.findUpToByEvaluator(id);
    InterviewRoomRes result = resultList.get(0);
    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "해당 평가자가 이미 진행한 면접 리스트 조회")
  @GetMapping("/past")
  public ResponseEntity<?> getPastInterviewRoomInfo(
      @ApiIgnore Authentication authentication) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    List<InterviewRoomRes> resultList = interviewRoomService.findPastByEvaluator(id);
    return ResponseEntity.status(200).body(resultList);
  }

  @ApiOperation(value = "해당 평가자에 배정된 면접별 지원자 리스트 조회")
  @GetMapping("/applicants")
  public ResponseEntity<?> getApplicantsInfo(@ApiIgnore Authentication authentication) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    HashMap<Long, List<ApplicantRes>> results = new HashMap<>();
    List<InterviewRoom> interviewRoomList = new ArrayList<>();
    Evaluator evaluator = evaluatorService.getEvaluatorById(id);
    List<EvaluatorInterviewRoom> evaluatorInterviewRoomList = evaluator.getEvaluatorInterviewRoomList();
    for (EvaluatorInterviewRoom eir : evaluatorInterviewRoomList) {
      interviewRoomList.add(eir.getInterviewRoom());
    }
    for (InterviewRoom ir : interviewRoomList) {
      List<ApplicantInterviewRoom> applicantInterviewRoomList = ir.getApplicantInterviewRoomList();
      Long key = ir.getId();
      List<ApplicantRes> tmp = new ArrayList<>();
      for (ApplicantInterviewRoom air : applicantInterviewRoomList) {
        tmp.add(new ApplicantRes(air.getApplicant()));
      }
      results.put(key, tmp);
    }

    return ResponseEntity.status(200).body(results);
  }


}
