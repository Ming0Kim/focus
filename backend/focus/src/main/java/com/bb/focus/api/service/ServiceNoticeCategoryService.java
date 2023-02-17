package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import java.util.List;
import java.util.Optional;

public interface ServiceNoticeCategoryService {

  List<ServiceNoticeCategory> findAll();

  Optional<ServiceNoticeCategory> findById(Long id);
}