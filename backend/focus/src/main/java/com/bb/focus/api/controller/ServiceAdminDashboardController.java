package com.bb.focus.api.controller;

import com.bb.focus.api.response.ProceedingProcessRes;
import com.bb.focus.api.service.ServiceAdminDashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "서비스 관리자 대시보드 API", tags = {"ServiceAdmin Dashboard"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servicedashboard")
@CrossOrigin("*")
public class ServiceAdminDashboardController {

  private final ServiceAdminDashboardService serviceAdminDashboardService;

  @ApiOperation(value = "현재 면접 진행중인 기업 리스트 및 프로세스 리스트")
  @GetMapping("/proceeding/companies/")
  public ResponseEntity<?> getProceedingCompanies(){

    List<ProceedingProcessRes> list = serviceAdminDashboardService.findProceedingCompanys();

    Map<String, List<ProceedingProcessRes>>results = list.stream()
        .collect(Collectors.groupingBy(ProceedingProcessRes::getCompanyName));

    return ResponseEntity.status(200).body(results);
  }

  @ApiOperation(value = "현재 면접 진행중인 기업 수")
  @GetMapping("/proceeding/companies/count/")
  public ResponseEntity<?> getProceedingCompaniesCount(){

    Long count = serviceAdminDashboardService.getProceedingCompanysCount();

    Map<String, Long> result = new HashMap<>();
    result.put("count",count);

    return ResponseEntity.status(200).body(result);
  }
}
