package com.bb.focus.api.controller;

import com.bb.focus.api.request.InterviewReq;
import com.bb.focus.api.service.InterviewService;
import com.bb.focus.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "면접(N차) API", tags = {"Interview"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/interview/round")
public class InterviewController {

  private final InterviewService interviewService;

  @ApiOperation(value = "면접(N차) 생성", notes = "프로세스에 n차 면접을 생성한다. 평가지 매핑 포함")
  @PostMapping("/{process-id}")
  public ResponseEntity<?> createInterview(
      @ApiIgnore Authentication authentication,
      @PathVariable("process-id") Long processId,
      @RequestBody @Valid @ApiParam(value = "면접 생성 정보", required = true) List<InterviewReq> interviewReq) {

    for (InterviewReq interview : interviewReq) {
      interviewService.createInterview(processId, interview);
    }
    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

}
