package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import com.bb.focus.db.repository.ServiceNoticeCategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serviceNoticeCategoryService")
public class ServiceNoticeCategoryServiceImpl implements ServiceNoticeCategoryService {

  @Autowired
  ServiceNoticeCategoryRepository serviceNoticeCategoryRepository;

  @Override
  public List<ServiceNoticeCategory> findAll() {
    return serviceNoticeCategoryRepository.findAll();
  }

  @Override
  public Optional<ServiceNoticeCategory> findById(Long id) {
    return serviceNoticeCategoryRepository.findById(id);
  }
}
