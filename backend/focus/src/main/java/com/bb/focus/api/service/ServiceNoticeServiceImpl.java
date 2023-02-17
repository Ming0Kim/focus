package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.ServiceNotice;
import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import com.bb.focus.db.repository.ServiceNoticeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("serviceNoticeService")
public class ServiceNoticeServiceImpl implements ServiceNoticeService {

  @Autowired
  ServiceNoticeRepository serviceNoticeRepository;


  @Override
  public ServiceNotice save(ServiceNotice serviceNotice) {
    return serviceNoticeRepository.save(serviceNotice);
  }

  @Override
  public Page<ServiceNotice> findAll(Pageable pageable) {
    return serviceNoticeRepository.findAll(pageable);
  }

  @Override
  public Optional<ServiceNotice> findById(Long id) {
    return serviceNoticeRepository.findById(id);
  }

  @Override
  public Page<ServiceNotice> findAllByCategoryOrderByCreatedAtDesc(Pageable pageable,
      ServiceNoticeCategory category) {
    return serviceNoticeRepository.findAllByCategoryOrderByCreatedAtDesc(pageable, category);
  }
}
