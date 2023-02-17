package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.ServiceNotice;
import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceNoticeService {

  ServiceNotice save(ServiceNotice serviceNotice);

  Page<ServiceNotice> findAll(Pageable pageable);

  Optional<ServiceNotice> findById(Long id);

  Page<ServiceNotice> findAllByCategoryOrderByCreatedAtDesc(Pageable pageable,
      ServiceNoticeCategory category);
}
