package com.bb.focus.api.controller;

import com.bb.focus.api.request.*;
import com.bb.focus.api.response.ApplicantDecisionRes;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.EvaluationSheetResultRes;
import com.bb.focus.api.service.DataProcessService;
import com.bb.focus.api.service.EvaluationService;
import com.bb.focus.common.auth.FocusUserDetails;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.repository.InterviewRoomRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Api(value = "평가 API", tags = {"Evaluation"})
@RestController
@CrossOrigin("*")
@RequestMapping("/api/interview")
public class EvaluationController {

    EvaluationService evaluationService;
    DataProcessService dataProcessService;
    private final InterviewRoomRepository interviewRoomRepository;

    public EvaluationController(EvaluationService evaluationS
            , DataProcessService dataProcessS,
                                InterviewRoomRepository interviewRoomRepository) {
        evaluationService = evaluationS;
        dataProcessService = dataProcessS;

        this.interviewRoomRepository = interviewRoomRepository;
    }


    @ApiOperation(value = "자신이 평가한 지원자 조회", notes = "평가자는 이전에 자신이 평가한 지원자 정보를 읽어온다.")
    @GetMapping("/evaluation/{interview-id}/{evaluator-id}/{applicant-id}")
    public ResponseEntity<?> GetApplicantEvaluationInfo(@PathVariable(name = "interview-id") Long interviewId,
                                                        @PathVariable(name = "evaluator-id") Long evaluatorId
            , @PathVariable(name = "applicant-id") Long applicantId) {

        List<EvaluationSheetResultRes> evaluationSheetResultResList = evaluationService.findApplicantEvaluation(evaluatorId, applicantId, interviewId);

        if (evaluationSheetResultResList == null) {
            return new ResponseEntity<String>("해당 사용자의 평가정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<EvaluationSheetResultRes>>(evaluationSheetResultResList, HttpStatus.OK);

    }

    @ApiOperation(value = "평가자의 사용자 평가 기능", notes = "평가 시 사용될 API")
    @Transactional
    @PostMapping("/evaluation")
    public ResponseEntity<?> EvaluationApplicant(
            @RequestBody @Valid EvaluationApplicantReq evaluationApplicantReq, @ApiIgnore Authentication authentication)
    {
//        Long interviewRoomId = Long.valueOf(evaluationApplicantReq.get("interviewRoomId").toString());
//        Long applicantId = Long.valueOf(evaluationApplicantReq.get("applicantId").toString());
//        String memo = evaluationApplicantReq.get("memo").toString();
//        List<Map<String,Object>> evaluationItemInfoList = (List<Map<String,Object>>) evaluationApplicantReq.get("evaluationItemInfoList");
        // evaluatorId 얻기

        System.out.println("==========================>" + evaluationApplicantReq.toString());
        FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
        Long evaluatorId = userDetails.getUser().getId();

        List<EvaluationInfo> evaluationItemInfoList = evaluationApplicantReq.getEvaluationItemInfoList();
        for(EvaluationInfo data : evaluationItemInfoList){
            System.out.println("evaluationItemId..............................."+data.getEvaluationItemId());
            System.out.println("content........................................"+data.getContent());
            System.out.println("score........................................."+data.getScore());
        }


   
//        System.out.println("==================================================================================>"+interviewRoomId);
//        System.out.println("==================================================================================>"+applicantId);
//        System.out.println("==================================================================================>"+memo);
//        System.out.println("==================================================================================>"+evaluationItemInfoList.size());

        List<ApplicantEvaluator> applicantEvaluatorList = interviewRoomRepository.findById(evaluationApplicantReq.getInterviewRoomId()).get().getApplicantEvaluatorList();
//
        Long applicantEvaluatorId = 0L;
        for (ApplicantEvaluator ae : applicantEvaluatorList) {
            if ((Objects.equals(ae.getApplicant().getId(), evaluationApplicantReq.getApplicantId()))
                    && (Objects.equals(ae.getEvaluator().getId(), evaluatorId))) {
                applicantEvaluatorId = ae.getId();
            }
        }
//
//        // 평가 항목 결과들 저장
        List<EvaluationItemInfoReq> evaluationItemInfoReqList = new ArrayList<>();
        for(EvaluationInfo eii:evaluationApplicantReq.getEvaluationItemInfoList()){
            EvaluationItemInfoReq evaluationItemInfoReq = new EvaluationItemInfoReq(eii);
            evaluationItemInfoReqList.add(evaluationItemInfoReq);
        }
        for (EvaluationItemInfoReq eii : evaluationItemInfoReqList) {
            evaluationService.ApplicantEvaluation(eii, applicantEvaluatorId, eii.getEvaluationItemId());
            evaluationService.UpdateApplicantEvaluationScore(applicantEvaluatorId);
        }
//
//        // 평가 메모 저장
        evaluationService.UpdateApplicantEvaluationMemo(applicantEvaluatorId, evaluationApplicantReq.getMemo());

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    
      @ApiOperation(value = "합불여부 체크", notes = "각 인터뷰 마지막에 합불여부를 결정하는 API")
    @Transactional
    @PostMapping("/decision/pass")
    public ResponseEntity<?> FinishInterview(@RequestBody List<Map<String, Object>> dataList) {
        List<DecisionReq> decisionReqList = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            Long processId = Long.valueOf(data.get("processId").toString());
            Map<String, Object> interviewResultReq = (Map<String, Object>) data.get("interviewResultReq");
            Long applicantId = Long.valueOf(interviewResultReq.get("applicantId").toString());
            String pass = interviewResultReq.get("pass").toString();
            InterviewResultReq interviewResultReq1 = new InterviewResultReq(applicantId,pass);
            DecisionReq decisionReq = new DecisionReq();
            decisionReq.setProcessId(processId);
            decisionReq.setInterviewResultReq(interviewResultReq1);
            decisionReqList.add(decisionReq);
        }

        if(!evaluationService.LoggingUserPass(decisionReqList)){
            return new ResponseEntity<String>("실패",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("성공",HttpStatus.OK);
    }

    
    @ApiOperation(value = "전형 현재 진행사항에 따른 합격자 값 가져오기", notes = "cur_step의 값을 통하여 이전 n차 면접 합격자 값을 가져오는 API")
    @GetMapping("/interview/applicants/{process-Id}")
    public ResponseEntity<?> GetApplicantPerPass(@PathVariable(name = "process-Id") Long processId) {
        List<ApplicantRes> applicantResList = evaluationService.findApplicantByPass(processId);

        if (applicantResList == null) {
            return new ResponseEntity<String>("전형 합격자 리스트 가져오기 실패", HttpStatus.OK);
        }

        return new ResponseEntity<List<ApplicantRes>>(applicantResList, HttpStatus.OK);
    }

    @ApiOperation(value = "통계 테이블 갱신", notes = "현재 존재하는 사용자들에 대한 통계데이터 갱신")
    @Transactional
    @PostMapping("/staticstic/givestatistics")
    public ResponseEntity<?> UpdateStatistic(@RequestBody @Valid Long processId) {
        Map<String, Integer> major = dataProcessService.UpdateStatisticTable(processId);
        dataProcessService.CreateMajorTable(major, processId);

        return new ResponseEntity<String>("통계데이터 업데이트 성공", HttpStatus.OK);
    }


    @ApiOperation(value = "평가내역 수정함수", notes = "평가내역 수정함수 decision/pass가 사용되기 전에만 가능")
    @Transactional
    @PutMapping("/modify/evaluation")
    public ResponseEntity<?> ModifyApplicantEvaluation(
            @RequestBody @Valid EvaluationApplicantUpdateReq evaluationApplicantUpdateReq, @ApiIgnore Authentication authentication) {

        // evaluatorId 얻기
        FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
        Long evaluatorId = userDetails.getUser().getId();
        List<ApplicantEvaluator> applicantEvaluatorList = interviewRoomRepository.findById(evaluationApplicantUpdateReq.getInterviewRoomId()).get().getApplicantEvaluatorList();

        // applicantEvaluatorId 구하기
        Long applicantEvaluatorId = 0L;
        for (ApplicantEvaluator ae : applicantEvaluatorList) {
            if ((Objects.equals(ae.getApplicant().getId(), evaluationApplicantUpdateReq.getApplicantId()))
                    && (Objects.equals(ae.getEvaluator().getId(), evaluatorId))) {
                applicantEvaluatorId = ae.getId();
            }
        }

        // 리스트로 받은 evaluationResult 업데이트 하기
        for (EvaluationResultUpdateReq erur : evaluationApplicantUpdateReq.getEvaluationResultUpdateReqList()) {
            evaluationService.ModifyApplicantEvaluation(erur);
        }

        // 평가 메모 업데이트 하기
        evaluationService.UpdateApplicantEvaluationMemo(applicantEvaluatorId, evaluationApplicantUpdateReq.getMemo());

        // 점수 업데이트 하기
        evaluationService.UpdateApplicantEvaluationScore(applicantEvaluatorId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    @ApiOperation(value = "평가자의 평가 메모 내용 갱신", notes = "평가자의 평가 메모를 갱신해주는 API")
    @Transactional
    @PutMapping("/save/memo")
    public ResponseEntity<?> EvaluatorSaveMemo(@RequestBody @Valid Long applicantEvaluatorId, @RequestBody @Valid String memo) {
        // applicantevaluatorId와 메모
        if (!evaluationService.UpdateApplicantEvaluationMemo(applicantEvaluatorId, memo)) {
            return new ResponseEntity<String>("메모 갱신 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("메모 저장 완료", HttpStatus.OK);
    }

    @ApiOperation(value = "면접실에 참여하는 지원자 리스트 조회")
    @GetMapping("/room/applicants/{interview-room-id}")
    public ResponseEntity<?> findAttendingApplicants(
            @PathVariable("interview-room-id") Long interviewRoomId) {

        List<ApplicantRes> result = evaluationService.findAttendingApplicants(interviewRoomId);
        return ResponseEntity.status(200).body(result);
    }

}
