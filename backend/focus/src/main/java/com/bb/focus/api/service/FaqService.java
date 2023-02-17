package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {

  Faq save(Faq faq);

  Page<Faq> findAll(Pageable pageable);

  Page<Faq> findByAuthRange(Pageable pageable, Byte userRole);

  Faq findById(Long faqId);

}
