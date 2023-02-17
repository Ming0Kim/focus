package com.bb.focus.api.controller;


import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.api.service.ApplicantService;
import com.bb.focus.api.service.InterviewRoomService;
import com.bb.focus.common.auth.FocusUserDetails;
import com.bb.focus.common.util.ImageUtil;
import com.bb.focus.db.entity.applicant.Applicant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"Applicant"})
@RestController
@RequestMapping("/api/applicants")
@CrossOrigin("*")
public class ApplicantController {
    @Autowired
    ImageUtil imageUtil;
    @Autowired
    ApplicantService applicantService;

    @Autowired
    InterviewRoomService interviewRoomService;

    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @GetMapping("/me")
    public ResponseEntity<ApplicantRes> getUserInfo(@ApiIgnore Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
        Long id = userDetails.getUser().getId();
        Applicant applicant = applicantService.getApplicantById(id);

        return ResponseEntity.status(200).body(ApplicantRes.of(applicant));
    }

    @ApiOperation(value = "해당 지원자에게 가장 가까운 면접 1개 정보 조회")
    @GetMapping("/next")
    public ResponseEntity<?> getNextInterviewRoomInfo(
            @ApiIgnore Authentication authentication) {

        FocusUserDetails userDetails = (FocusUserDetails) authentication.getDetails();
        Long id = userDetails.getUser().getId();
        List<InterviewRoomRes> resultList = interviewRoomService.findUpToByApplicant(id);
        InterviewRoomRes result = resultList.get(0);
        return ResponseEntity.status(200).body(result);
    }

}
