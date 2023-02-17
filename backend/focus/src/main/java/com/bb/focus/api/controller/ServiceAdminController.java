package com.bb.focus.api.controller;

import com.bb.focus.api.request.CompanyAdminRegisterPostReq;
import com.bb.focus.api.request.ServiceAdminRegisterPostReq;
import com.bb.focus.api.request.ServiceNoticeReq;
import com.bb.focus.api.response.CompanyAdminRes;
import com.bb.focus.api.response.ServiceAdminRes;
import com.bb.focus.api.response.ServiceNoticeRes;
import com.bb.focus.api.service.CompanyAdminService;
import com.bb.focus.api.service.ProcessService;
import com.bb.focus.api.service.ServiceAdminService;
import com.bb.focus.api.service.ServiceNoticeCategoryService;
import com.bb.focus.api.service.ServiceNoticeService;
import com.bb.focus.common.auth.FocusUserDetails;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.bb.focus.db.entity.admin.ServiceNotice;
import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import com.bb.focus.db.entity.company.CompanyAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "서비스 관리자 API", tags = {"ServiceUser"})
@RestController
@CrossOrigin("*")
@RequestMapping("/api/serviceusers")
public class ServiceAdminController {

  @Autowired
  ServiceAdminService serviceAdminService;

  @Autowired
  CompanyAdminService companyAdminService;

  @Autowired
  ServiceNoticeCategoryService serviceNoticeCategoryService;

  @Autowired
  ServiceNoticeService serviceNoticeService;

  @Autowired
  ProcessService processService;

  @PostMapping()
  @ApiOperation(value = "관리자 계정 생성", notes = "<strong>아이디와 패스워드</strong>를 통해 관리자의 계정을 생성한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<? extends BaseResponseBody> register(
      @RequestBody @Valid @ApiParam(value = "회원가입 정보", required = true) ServiceAdminRegisterPostReq registerInfo) {

    //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
    ServiceAdmin serviceAdmin = serviceAdminService.createUser(registerInfo);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @PostMapping("/accounts")
  @ApiOperation(value = "기업 관리자 계정 생성", notes = "<strong>아이디와 패스워드</strong>를 통해 기업관리자의 계정을 생성해준다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<? extends BaseResponseBody> registerCompanyAdmin(
      @RequestBody @Valid @ApiParam(value = "회원가입 정보", required = true) CompanyAdminRegisterPostReq registerInfo) {

    CompanyAdmin companyAdmin = companyAdminService.createCompanyAdmin(registerInfo);
    Long sequenceId = companyAdmin.getId();
    companyAdmin.setUserId(companyAdmin.getUserId() + sequenceId);
    CompanyAdmin companyAdminRevised = companyAdminService.updateCompanyAdminUserId(companyAdmin);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @GetMapping("/accounts")
  @ApiOperation(value = "기업 계정 리스트 정보 조회", notes = "서비스 관리자가 조회할 수 있으며, 등록되어 있는 기업 계정 리스트 정보를 응답한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<List<CompanyAdminRes>> getCompanyAdminInfoList() {
    List<CompanyAdmin> companyAdminList = companyAdminService.getAll();
    List<CompanyAdminRes> companyAdminResList = new ArrayList<>();
    for (CompanyAdmin ca : companyAdminList) {
      companyAdminResList.add(CompanyAdminRes.of(ca));
    }
    return ResponseEntity.status(200).body(companyAdminResList);
  }

  @GetMapping("/accounts/contracts")
  @ApiOperation(value = "계약 중인 기업 계정 리스트 정보 조회", notes = "서비스 관리자가 조회할 수 있으며, 등록되어 있는 기업 계정 중 계약 중인 기업 계정 리스트 정보를 응답한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<List<CompanyAdminRes>> getCompanyAdminOnContractInfoList() {
    List<CompanyAdmin> companyAdminList = companyAdminService.getAllContainsToday();
    List<CompanyAdminRes> companyAdminResList = new ArrayList<>();
    for (CompanyAdmin ca : companyAdminList) {
      companyAdminResList.add(CompanyAdminRes.of(ca));
    }
    return ResponseEntity.status(200).body(companyAdminResList);
  }

  @GetMapping("/accounts/contracts/number")
  @ApiOperation(value = "계약 중인 기업 계정 개수 정보 조회", notes = "서비스 관리자가 조회할 수 있으며, 등록되어 있는 기업 계정 중 계약 중인 기업 계정 리스트 정보를 응답한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<?> getCompanyAdminOnContractNumber() {
    List<CompanyAdmin> companyAdminList = companyAdminService.getAllContainsToday();
    List<CompanyAdminRes> companyAdminResList = new ArrayList<>();
    for (CompanyAdmin ca : companyAdminList) {
      companyAdminResList.add(CompanyAdminRes.of(ca));
    }
    return ResponseEntity.status(200).body(companyAdminResList.size());
  }


  @GetMapping("/accounts/{company-admin-id}")
  @ApiOperation(value = "기업 계정 정보 조회", notes = "서비스 관리자가 조회할 수 있으며, 등록되어 있는 기업 계정 정보를 응답한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<CompanyAdminRes> getCompanyAdminInfo(String userId) {
    CompanyAdmin companyAdmin = companyAdminService.getCompanyAdminByUserId(userId);
    return ResponseEntity.status(200).body(CompanyAdminRes.of(companyAdmin));
  }

  @PutMapping("/accounts/{company-admin-id}")
  @ApiOperation(value = "기업 계정 정보 조회", notes = "서비스 관리자가 조회할 수 있으며, 등록되어 있는 기업 계정 정보를 응답한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<? extends BaseResponseBody> updateCompanyAdminInfo(
      @RequestBody @Valid @ApiParam(value = "기업 관리자 정보 수정 내역", required = true) CompanyAdminRegisterPostReq updateInfo) {
    Long companyAdminId = companyAdminService.updateCompanyAdminByUserInfo(updateInfo);
    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
  @GetMapping("/me")
  public ResponseEntity<?> getUserInfo(@ApiIgnore Authentication authentication) {
    /**
     * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
     * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
     */

    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    ServiceAdmin serviceAdmin = serviceAdminService.getServiceAdminById(id);

    return ResponseEntity.status(200).body(ServiceAdminRes.of(serviceAdmin));
//      throw new RestApiException(UserErrorCode.INACTIVE_USER);
  }

  @ApiOperation(value = "서비스 공지 사항 생성", notes = "서비스 공지 사항을 생성한다.")
  @PostMapping("/notices")
  public ResponseEntity<? extends BaseResponseBody> createServiceNotice(
      @RequestBody @Valid @ApiParam(value = "생성하고자 하는 서비스 공지 사항 내용", required = true) ServiceNoticeReq.Create serviceNoticeReq,
      @ApiIgnore Authentication authentication
  ) {
    FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
    Long id = userDetails.getUser().getId();
    ServiceNotice serviceNotice = new ServiceNotice();
    ServiceAdmin serviceAdmin = serviceAdminService.getServiceAdminById(id);
    Optional<ServiceNoticeCategory> serviceNoticeCategory = serviceNoticeCategoryService.findById(
        serviceNoticeReq.getCategoryId());
    serviceNotice.setServiceAdmin(serviceAdmin);
    serviceNotice.setCategory(serviceNoticeCategory.get());
    serviceNotice.setTitle(serviceNoticeReq.getTitle());
    serviceNotice.setContent(serviceNoticeReq.getContent());
    serviceNoticeService.save(serviceNotice);
    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  // http://localhost:8080/serviceusers/notices/?page=2&size=3
  @ApiOperation(value = "서비스 공지 사항 리스트 조회", notes = "서비스 공지 사항 리스트를 조회한다.")
  @GetMapping("/notices")
  public ResponseEntity<?> getServiceNoticeList(Pageable pageable) {
    Page<ServiceNoticeRes> results = serviceNoticeService.findAll(pageable)
        .map(ServiceNoticeRes::from);
    return ResponseEntity.status(200).body(results);
  }

  // http://localhost:8080/serviceusers/notices/category/2?page=1&size=3
  @ApiOperation(value = "카테고리별 서비스 공지 사항 리스트 조회", notes = "카테고리별 서비스 공지 사항 리스트를 조회한다.")
  @GetMapping("/notices/category/{category-id}")
  public ResponseEntity<?> getServiceNoticeListByCategory(Pageable pageable,
      @PathVariable("category-id") Long categoryId
  ) {
    ServiceNoticeCategory category = serviceNoticeCategoryService.findById(categoryId).get();
    Page<ServiceNoticeRes> results = serviceNoticeService.findAllByCategoryOrderByCreatedAtDesc(
            pageable, category)
        .map(ServiceNoticeRes::from);
    return ResponseEntity.status(200).body(results);
  }

  @ApiOperation(value = "서비스 공지 사항 상세 조회", notes = "서비스 공지 사항 리스트를 조회한다.")
  @GetMapping("/notices/{service-notice-id}")
  public ResponseEntity<?> getServiceNoticeInfo(
      @PathVariable("service-notice-id") Long serviceNoticeId
  ) {
    ServiceNotice serviceNotice = serviceNoticeService.findById(serviceNoticeId).get();
    ServiceNoticeRes serviceNoticeRes = new ServiceNoticeRes(
        serviceNotice.getId(),
        serviceNotice.getServiceAdmin().getId(),
        serviceNotice.getCategory().getName(),
        serviceNotice.getTitle(),
        serviceNotice.getCreatedAt(),
        serviceNotice.getContent()
    );
    return ResponseEntity.status(200).body(serviceNoticeRes);
  }


  @ApiOperation(value = "면접 진행중인 기업 프로세스 개수 조회")
  @GetMapping("/dashboard/process/number")
  public ResponseEntity<?> getProcessNumber(
  ) {
    int ret = processService.getProcessGoingOnNumber();
    return ResponseEntity.status(200).body(ret);
  }
}