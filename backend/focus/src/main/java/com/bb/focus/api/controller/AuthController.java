package com.bb.focus.api.controller;

import com.bb.focus.api.request.UserReq;
import com.bb.focus.api.response.UserLoginPostRes;
import com.bb.focus.api.response.UserLogoutPostRes;
import com.bb.focus.api.service.ApplicantService;
import com.bb.focus.api.service.CompanyAdminService;
import com.bb.focus.api.service.EvaluatorService;
import com.bb.focus.api.service.ServiceAdminService;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.common.util.JwtTokenUtil;
import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.evaluator.Evaluator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth"})
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

  @Autowired
  ServiceAdminService serviceAdminService;

  @Autowired
  CompanyAdminService companyAdminService;

  @Autowired
  EvaluatorService evaluatorService;

  @Autowired
  ApplicantService applicantService;

  @Autowired
  RedisTemplate redisTemplate;


  @PostMapping("/login")
  @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
      @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
      @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
      @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
  })
  public ResponseEntity<UserLoginPostRes> login(
      @RequestBody @Valid @ApiParam(value = "로그인 정보", required = true) UserReq.Login loginInfo) {
    String userId = loginInfo.getId();
    String password = loginInfo.getPassword();
    ServiceAdmin serviceAdmin = serviceAdminService.getServiceAdminByUserId(userId);
    if (serviceAdmin != null) {
      if (password.equals(serviceAdmin.getPwd())) {
        // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
        Long sequenceId = serviceAdmin.getId();
        return ResponseEntity.ok(
            UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(sequenceId), (byte) 1, sequenceId));
      }
    } else {
      CompanyAdmin companyAdmin = companyAdminService.getCompanyAdminByUserId(userId);
      if (companyAdmin != null) {
        if (password.equals(companyAdmin.getPwd())) {
          // 계정이 만료된 경우 로그인 실패로 응답
          if (!canLogin(companyAdmin.getEndDate())) {
            return ResponseEntity.status(401)
                .body(UserLoginPostRes.of(401, "End of Contract", null, (byte) 0, 0L));
          }
          Long sequenceId = companyAdmin.getId();
          // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
          return ResponseEntity.ok(
              UserLoginPostRes.of(200, "Success",
                  JwtTokenUtil.getToken(sequenceId), (byte) 2, sequenceId));
        }
      } else {
        Evaluator evaluator = evaluatorService.getEvaluatorByUserId(userId);
        if (evaluator != null) {
          if (password.equals(evaluator.getPwd())) {
            // 계정이 만료된 경우 로그인 실패로 응답
            if (!canLogin(evaluator.getExpireDate())) {
              return ResponseEntity.status(401)
                  .body(UserLoginPostRes.of(401, "End of Contract", null, (byte) 0, 0L));
            }
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            Long sequenceId = evaluator.getId();
            return ResponseEntity.ok(
                UserLoginPostRes.of(200, "Success",
                    JwtTokenUtil.getToken(sequenceId), (byte) 3, sequenceId));
          }
        } else {
          Applicant applicant = applicantService.getApplicantByUserId(userId);
          if (password.equals(applicant.getPwd())) {
            // 계정이 만료된 경우 로그인 실패로 응답
            if (!canLogin(applicant.getExpireDate())) {
              return ResponseEntity.status(401)
                  .body(UserLoginPostRes.of(401, "End of Contract", null, (byte) 0, 0L));
            }
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            Long sequenceId = applicant.getId();
            return ResponseEntity.ok(
                UserLoginPostRes.of(200, "Success",
                    JwtTokenUtil.getToken(sequenceId), (byte) 4, sequenceId));
          }
        }
      }
    }
    // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
    return ResponseEntity.status(401).

        body(UserLoginPostRes.of(401, "Invalid Password", null, (byte) 0, 0L));
  }

  // 계정 만료 확인
  private boolean canLogin(LocalDateTime endDate) {
    LocalDateTime now = LocalDateTime.now();
    if (now.isBefore(endDate) || now.isEqual(endDate)) {
      return true;
    }
    return false;
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(
      @RequestBody @Valid @ApiParam(value = "로그아웃 정보", required = true) UserReq.Logout logoutInfo) {
    String userId = JwtTokenUtil.getUserId(logoutInfo.getAccessToken());
    System.out.println("userId : " + userId);
    Long expiration = JwtTokenUtil.getLeftExpiration(logoutInfo.getAccessToken());
    System.out.println("expiration : " + expiration);
    redisTemplate.opsForValue()
        .set("Bearer " + logoutInfo.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(
        UserLogoutPostRes.of(200, "Success", userId));
  }
}
