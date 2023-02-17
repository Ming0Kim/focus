package com.bb.focus.api.controller;

import com.bb.focus.api.request.ApplicantInfoReq;
import com.bb.focus.api.request.EvaluatorInfoReq;
import com.bb.focus.api.response.ApplicantDecisionRes;
import com.bb.focus.api.response.ApplicantDetailRes;
import com.bb.focus.api.response.ApplicantLogRes;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.CompanyAdminRes;
import com.bb.focus.api.response.EvaluatorDetailRes;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.api.response.ProcessRes;
import com.bb.focus.api.service.ApplicantService;
import com.bb.focus.api.service.CompanyAdminService;
import com.bb.focus.api.service.EvaluationService;
import com.bb.focus.api.service.EvaluatorService;
import com.bb.focus.common.auth.FocusUserDetails;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.interview.InterviewRoom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import javax.mail.MessagingException;

@Api(value = "기업관리자 API", tags = {"CompanyAdmin"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companyusers")
@CrossOrigin("*")
public class CompanyAdminController {
  @Autowired
  private final EvaluationService evaluationService;
  @Autowired
  private final EvaluatorService evaluatorService;
  @Autowired
  private final ApplicantService applicantService;
  @Autowired
  private final CompanyAdminService companyAdminService;


  @ApiOperation(value = "평가자 계정 생성", notes = "기업관리자로부터 입력받은 정보로 평가자 계정을 생성한다.")
  @PostMapping("/evaluators")
  public ResponseEntity<?> createEvaluator(
      @ApiIgnore Authentication authentication,
      @RequestBody @Valid @ApiParam(value = "평가자 계정 생성 정보", required = true) List<EvaluatorInfoReq> evaluatorInfoList) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    for (EvaluatorInfoReq evaluator : evaluatorInfoList) {
      evaluatorService.create(companyAdminId, evaluator);
    }

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "지원자 계정 생성", notes = "기업관리자로부터 입력받은 정보로 지원자 계정을 생성한다.")
  @PostMapping("/applicants/{process-id}")
  public ResponseEntity<? extends BaseResponseBody> createApplicant(
      @ApiIgnore Authentication authentication,
      @PathVariable("process-id") Long processId,
      @RequestBody @Valid @ApiParam(value = "지원자 계정 생성 정보", required = true) List<ApplicantInfoReq> ApplicantInfoList) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    for (ApplicantInfoReq applicant : ApplicantInfoList) {
      applicantService.create(companyAdminId, applicant, processId);
    }

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "평가자 ID, PWD 자동생성 : 단건", notes = "평가자의 1명의 id와 pwd를 자동생성한다.")
  @PostMapping("/evaluators/create/{evaluator-id}")
  public ResponseEntity<? extends BaseResponseBody> autoSetEvaluatorAccount(
      @ApiIgnore Authentication authentication,
      @PathVariable("evaluator-id") Long id) throws MessagingException {

    evaluatorService.autoAssignAccount(id);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "모든 평가자 ID, PWD 자동생성", notes = "사내 모든 평가자의 ID, PWD를 일괄 자동생성한다.")
  @PostMapping("/evaluators/create/all")
  public ResponseEntity<? extends BaseResponseBody> autoSetTotalEvaluatorAccount(
      @ApiIgnore Authentication authentication) throws MessagingException {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    List<Evaluator> evaluators = evaluatorService.findAllEvaluators(companyAdminId);

    for (Evaluator evaluator : evaluators) {
      evaluatorService.autoAssignAccount(evaluator.getId());
    }
    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "지원자 ID, PWD 자동생성 : 단건", notes = "지원자의 1명의 id와 pwd를 자동생성한다.")
  @PostMapping("/applicants/create/{applicant-id}")
  public ResponseEntity<? extends BaseResponseBody> autoSetApplicantAccount(
      @ApiIgnore Authentication authentication,
      @PathVariable("applicant-id") Long id) throws MessagingException {

    applicantService.autoAssignAccount(id);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  /**
   * company-admin-id @PathVariable로 사용한다.....
   */
  @ApiOperation(value = "모든 지원자 ID, PWD 자동생성", notes = "사내 모든 지원자의 ID, PWD를 일괄 자동생성한다.")
  @PostMapping("/applicants/create/all")
  public ResponseEntity<? extends BaseResponseBody> autoSetTotalApplicantAccount(
      @ApiIgnore Authentication authentication) throws MessagingException {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    List<Applicant> applicants = applicantService.findAllApplicants(companyAdminId);

    for (Applicant applicant : applicants) {
      applicantService.autoAssignAccount(applicant.getId());
    }

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }


  @ApiOperation(value = "평가자 계정 수정", notes = "기업관리자로부터 입력받은 수정 정보로 평가자 계정을 수정한다.")
  @PutMapping("/evaluators/{evaluator-id}")
  public ResponseEntity<Map<String, Long>> updateEvaluator(
      @ApiIgnore Authentication authentication,
      @PathVariable("evaluator-id") Long id,
      @RequestBody @Valid @ApiParam(value = "평가자 계정 기본 정보", required = true) EvaluatorInfoReq evaluatorInfo) {

    evaluatorService.updateEvaluatorInfo(id, evaluatorInfo);

    Map<String, Long> result = new HashMap<>();
    result.put("id", id);

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "지원자 계정 수정", notes = "기업관리자로부터 입력받은 수정 정보로 지원자 계정을 수정한다.")
  @PutMapping("/applicants/{applicant-id}")
  public ResponseEntity<Map<String, Long>> updateApplicant(
      @ApiIgnore Authentication authentication,
      @PathVariable("applicant-id") Long id,
      @RequestBody @Valid @ApiParam(value = "지원자 계정 기본 정보", required = true) ApplicantInfoReq applicantInfo) {

    applicantService.updateApplicantInfo(id, applicantInfo);

    Map<String, Long> result = new HashMap<>();
    result.put("id", id);

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "평가자 계정 삭제", notes = "기업관리자가 평가자 계정을 삭제한다.")
  @DeleteMapping("/evaluators/{evaluator-id}")
  public ResponseEntity<? extends BaseResponseBody> deleteEvaluator(
      @ApiIgnore Authentication authentication,
      @PathVariable("evaluator-id") Long id) {

    evaluatorService.removeEvaluator(id);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "사내 평가자 계정 리스트 조회", notes = "사내 평가자 계정 리스트를 조회한다.")
  @GetMapping(value = {
      "/evaluators/list",
      "/evaluators/list/{search-name}"
  })
  public ResponseEntity<Page<EvaluatorRes>> getEvaluators(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "search-name", required = false) String search,
      @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    Page<EvaluatorRes> evaluators = evaluatorService.findAllEvaluatorsUsePaging(pageable, search, companyAdminId);

    return ResponseEntity.status(200).body(evaluators);
  }

  @ApiOperation(value = "사내 전체 부서 리스트 조회(이름)", notes = "사내 평가자들의 모든 부서 이름 리스트를 조회한다.")
  @GetMapping(value = "/departments")
  public ResponseEntity<List<String>> getDepartments(@ApiIgnore Authentication authentication) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    List<String> departments = evaluatorService.getDepartments(companyAdminId);
    return ResponseEntity.status(200).body(departments);
  }

  @ApiOperation(value="사내 평가자 계정 리스트 조회 : 부서별", notes = "부서별 사내 평가자 계정 리스트를 조회한다.")
  @GetMapping(value = "/evaluators/departments/{departments}")
  public ResponseEntity<Page<EvaluatorRes>> getDepartmentsEvaluators(
          @ApiIgnore Authentication authentication,
          @PathVariable("departments") List<String> departmentList,
          Pageable pageable) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    Page<EvaluatorRes> evaluators = null;

    if(!departmentList.isEmpty()){
      evaluators = evaluatorService.findDepartmentEvaluators(pageable, departmentList, companyAdminId);
    }

    return ResponseEntity.status(200).body(evaluators);
  }

  @ApiOperation(value = "지원자 계정 삭제", notes = "기업관리자가 지원자 계정을 삭제한다.")
  @DeleteMapping("/applicants/{applicant-id}")
  public ResponseEntity<? extends BaseResponseBody> deleteApplicant(
      @ApiIgnore Authentication authentication,
      @PathVariable("applicant-id") Long id) {

    applicantService.removeApplicant(id);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "사내 지원자 계정 리스트 조회", notes = "사내 지원자 계정 리스트를 조회한다.")
  @GetMapping(value = {
          "/applicants/{process-id}/list",
          "/applicants/{process-id}/list/{search-name}"
  })
  public ResponseEntity<Page<ApplicantRes>> getApplicants(
      @ApiIgnore Authentication authentication,
      @PathVariable(value = "process-id", required = true) Long processId,
      @PathVariable(value = "search-name", required = false) String search,
      @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long companyAdminId = userDetails.getUser().getId();

    Page<ApplicantRes> applicants = applicantService.findAllApplicantsUsePaging(pageable, search, companyAdminId, processId);

    return ResponseEntity.status(200).body(applicants);
  }

  @ApiOperation(value = "평가자 상세 조회", notes = "특정 평가자 계정의 상세 정보를 조회한다.")
  @GetMapping("/evaluators/{evaluator-id}")
  public ResponseEntity<EvaluatorDetailRes> getEvaluatorDetail(
      @ApiIgnore Authentication authentication,
      @PathVariable("evaluator-id") Long id) {

    Evaluator evaluator = evaluatorService.findEvaluator(id);

    EvaluatorDetailRes result = new EvaluatorDetailRes(evaluator);

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "지원자 상세 조회", notes = "특정 지원자 계정의 상세 정보를 조회한다.")
  @GetMapping("/applicants/{applicant-id}")
  public ResponseEntity<ApplicantDetailRes> getApplicantDetail(
      @ApiIgnore Authentication authentication,
      @PathVariable("applicant-id") Long id) {

    Applicant applicant = applicantService.findApplicant(id);

    ApplicantDetailRes result = new ApplicantDetailRes(applicant);

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
  @GetMapping("/me")
  public ResponseEntity<CompanyAdminRes> getUserInfo(@ApiIgnore Authentication authentication) {
    /**
     * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
     * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
     */
    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    CompanyAdmin companyAdmin = companyAdminService.getCompanyAdminById(id);

    return ResponseEntity.status(200).body(CompanyAdminRes.of(companyAdmin));
  }

  @ApiOperation(value = "인터뷰에 따른 지원자 합불 여부 처리", notes = "인터뷰 넘버와 해당 인터뷰에 따른 지원자 결과 변경")
  @PutMapping("/pass/{process-id}/{applicant-id}")
  public ResponseEntity<?> ApplicantPassProcess(@PathVariable(name="process-id")Long processId,@PathVariable(name="applicant-id")Long applicantId){
    String message  = "";



    return new ResponseEntity<String>(message, HttpStatus.OK);
  }
  @ApiOperation(value= "해당 기업에 속한 모든 평가자 수를 호출하는 함수", notes="기업 번호를 통하여 해당 기업에 속한 평가자 수 반환")
  @GetMapping("/evaluatorCount/{company-id}")
  public ResponseEntity<?> GetEvaluatorCount(@PathVariable(name="company-id") Long companyId)  {
    int evaluatorCount = companyAdminService.getAllEvaluationCount(companyId);

    return new ResponseEntity<Integer>(evaluatorCount,HttpStatus.OK);
  }

  @ApiOperation(value= "해당 기업에 속한 모든 지원자 수를 호출하는 함수", notes="기업 번호를 통하여 해당 기업에 속한 지원자 수 반환")
  @GetMapping("/applicantCount/{company-id}")
  public ResponseEntity<?> GetApplicantCount(@PathVariable(name="company-id") Long companyId)  {
    int applicantCount = companyAdminService.getAllApplicantCount(companyId);

    return new ResponseEntity<Integer>(applicantCount,HttpStatus.OK);
  }
  @ApiOperation(value= "해당 프로세스에서 진행 예정인 면접을 반환하는 함수", notes="해당 프로세스에서 진행예정인 면접을 반환하는 API")
  @GetMapping("/interviewRoom/reserve/{process-id}")
  public ResponseEntity<?> GetReservedInterview(@PathVariable(name="process-id")Long processId){
    List<InterviewRoomRes> interviewRoomResList =companyAdminService.getAllReservedInterview(processId);

    if(interviewRoomResList == null){
      return new ResponseEntity<String>("면접실 정보 가져오기 실패",HttpStatus.OK);
    }

    return new ResponseEntity<List<InterviewRoomRes>>(interviewRoomResList,HttpStatus.OK);
  }

  @ApiOperation(value= "기업 관리자 별 모든 전체 전형리스트", notes="기업관리자 별로 모든 프로세스 정보를 반환하는 API")
  @GetMapping("/process/{company-id}")
  public ResponseEntity<?>  GetAllCompanyProcess(@PathVariable(name="company-id")Long companyId){
    List<ProcessRes> processResList = companyAdminService.getAllProcess(companyId);

    if(processResList == null){
      return new ResponseEntity<String>("전형 데이터 읽어오기 실패",HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<List<ProcessRes>>(processResList,HttpStatus.OK);
  }

  @ApiOperation(value= "차수 별 합격자", notes="차수 별 합격자 반환하는 API")
  @GetMapping("/process/pass/{process-id}")
  public ResponseEntity<?>  GetStepPerPassApplicant(@PathVariable(name="process-id") Long processId){
    List<ApplicantDecisionRes> applicantResList = evaluationService.findApplicantDecisionByPass(processId);

    if (applicantResList == null) {
      return new ResponseEntity<String>("전형 합격자 리스트 가져오기 실패", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<List<ApplicantDecisionRes>>(applicantResList, HttpStatus.OK);
  }

  @ApiOperation(value= "종료된 프로세스 정보 가져와서 정보 출력", notes="스텝이 종료된 프로세스 정보만 출력하는 API")
  @GetMapping("/finished/process/info/{company-id}")
  public ResponseEntity<?> GetFinishStepProcess(@PathVariable(name="company-id")Long companyId){
    List<ProcessRes> processResList=companyAdminService.getFinishStepPerProcessInfo(companyId);

    if(processResList == null){
      return new ResponseEntity<String>("프로세스 데이터 가져오기 실패",HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<List<ProcessRes>>(processResList,HttpStatus.OK);
  }


}
