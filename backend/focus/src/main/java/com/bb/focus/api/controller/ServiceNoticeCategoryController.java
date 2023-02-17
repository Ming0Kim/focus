package com.bb.focus.api.controller;

import com.bb.focus.api.service.ServiceNoticeCategoryService;
import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "카테고리 API", tags = {"ServiceNoticeCategory"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class ServiceNoticeCategoryController {

  private final ServiceNoticeCategoryService serviceNoticeCategoryService;

  @ApiOperation(value = "서비스 공지사항 카테고리 조회", notes = "서비스 관리자가 작성하는 서비스 공지사항 공지글의 카테고리를 조회합니다.")
  @GetMapping("/")
  public ResponseEntity<?> getCategories() {

    List<ServiceNoticeCategory> results = serviceNoticeCategoryService.findAll();

    return ResponseEntity.status(200).body(results);
  }

}
