package com.bb.focus.api.controller;

import com.bb.focus.api.request.FaqReq;
import com.bb.focus.api.response.FaqRes;
import com.bb.focus.api.service.FaqService;
import com.bb.focus.api.service.ServiceAdminService;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.db.entity.admin.Faq;
import com.bb.focus.db.entity.admin.ServiceAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "FAQ API", tags = {"FAQ"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/faq")
public class FaqController {

  private final FaqService faqService;

  private final ServiceAdminService serviceAdminService;

  @ApiOperation(value = "FAQ 글 생성")
  @PostMapping("/")
  public ResponseEntity<? extends BaseResponseBody> createFaq(
      @RequestBody @ApiParam(value = "FAQ 생성 정보", required = true) FaqReq.Create faq
  ) {
    Faq saveFaq = new Faq();
    saveFaq.setTitle(faq.getTitle());
    saveFaq.setContent(faq.getContent());
    saveFaq.setAuthRange(faq.getAuthRange());
    ServiceAdmin serviceAdmin = serviceAdminService.getServiceAdminById(faq.getServiceAdminId());
    saveFaq.setServiceAdmin(serviceAdmin);
    faqService.save(saveFaq);

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

  // http://localhost:8080/faq/?page=2&size=3&userRole=2 형식
  @ApiOperation(value = "FAQ 리스트 조회", notes = "유저 계정에 따라 서로 다른 FAQ 리스트를 반환한다.")
  @GetMapping("/")
  public ResponseEntity<?> getFaqList(@PageableDefault(size=2) Pageable pageable,
      @ApiParam(value = "유저의 권한(기업 관리자 : 2, 평가자 : 3, 지원자 : 4", required = true) Byte userRole) {

    Page<FaqRes> results = null;

    switch (userRole) {
      case 1: // 서비스 관리자
        results = faqService.findAll(pageable).map(FaqRes::from);
        break;
      case 2: // 기업 관리자
        results = faqService.findAll(pageable).map(FaqRes::from);
        break;
      case 3: // 평가자
        results = faqService.findByAuthRange(pageable, (byte) 3).map(FaqRes::from);
        break;
      case 4: // 지원자
        results = faqService.findByAuthRange(pageable, (byte) 4).map(FaqRes::from);
        break;
    }

    return ResponseEntity.status(200).body(results);
  }


  @ApiOperation(value = "FAQ 글 상세 조회", notes = "특정 FAQ 글 정보를 반환한다.")
  @GetMapping("/{faq-id}")
  public ResponseEntity<?> getFaqInfo(@PathVariable("faq-id") Long faqId) {

    Faq faq = faqService.findById(faqId);

    FaqRes result =new FaqRes();
    result.setId(faq.getId());
    result.setServiceAdminId(faq.getServiceAdmin().getId());
    result.setTitle(faq.getTitle());
    result.setContent(faq.getContent());
    result.setCreatedAt(faq.getCreatedAt());
    result.setAuthRange(faq.getAuthRange());

    return ResponseEntity.status(200).body(result);
  }
}
