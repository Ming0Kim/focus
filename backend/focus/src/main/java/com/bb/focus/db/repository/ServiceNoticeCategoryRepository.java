package com.bb.focus.db.repository;

import com.bb.focus.db.entity.admin.ServiceNoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceNoticeCategoryRepository extends JpaRepository<ServiceNoticeCategory, Long>,
    ServiceNoticeCategoryCustomRepository {


}
