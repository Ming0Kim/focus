package com.bb.focus.db.repository;

import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.db.entity.applicant.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicantCustomRepository {

  List<Applicant> findAllApplicantsByCompanyAdminId(Long companyAdminId);

  Applicant findApplicantByUserId(String userId);

  Applicant findApplicantById(Long id);

  Page<ApplicantRes> findAllApplicantsWithPaging(Pageable pageable, String search, Long companyAdminId, Long processId);

}
