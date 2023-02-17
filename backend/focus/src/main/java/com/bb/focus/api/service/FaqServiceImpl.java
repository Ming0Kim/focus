package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.Faq;
import com.bb.focus.db.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("faqService")
public class FaqServiceImpl implements FaqService {

  @Autowired
  FaqRepository faqRepository;

  @Override
  public Faq save(Faq faq) {
    return faqRepository.save(faq);
  }

  @Override
  public Page<Faq> findAll(Pageable pageable) {
    return faqRepository.findAllByOrderByCreatedAtDesc(pageable);
  }

  @Override
  public Page<Faq> findByAuthRange(Pageable pageable, Byte userRole) {
    return faqRepository.findByAuthRangeOrderByCreatedAtDesc(pageable, userRole);
  }

  @Override
  public Faq findById(Long faqId) {
    return faqRepository.findById(faqId).get();
  }
}
