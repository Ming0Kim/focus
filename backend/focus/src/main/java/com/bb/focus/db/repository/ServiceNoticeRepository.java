package com.bb.focus.db.repository;

import com.bb.focus.db.entity.admin.ServiceNotice;
import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceNoticeRepository extends JpaRepository<ServiceNotice, Long>,
    ServiceNoticeCustomRepository {

  Page<ServiceNotice> findAllByCategoryOrderByCreatedAtDesc(Pageable pageable,
      ServiceNoticeCategory category);

}
