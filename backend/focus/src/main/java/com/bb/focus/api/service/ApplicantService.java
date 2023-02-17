package com.bb.focus.api.service;

import com.bb.focus.api.request.ApplicantInfoReq;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.db.entity.applicant.Applicant;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicantService {

  public Long create(Long comapnyAdminId, ApplicantInfoReq applicantInfoReq, Long processId);

  public void autoAssignAccount(Long id) throws MessagingException;

  public Long updateApplicantInfo(Long id, ApplicantInfoReq applicantInfoReq);

  public void removeApplicant(Long id);

  public List<Applicant> findAllApplicants(Long companyAdminId);

  public Applicant findApplicant(Long id);

  public Applicant getApplicantByUserId(String userId);

  public Applicant getApplicantById(Long id);

  Page<ApplicantRes> findAllApplicantsUsePaging(Pageable pageable, String search, Long companyAdminId, Long processId);
}
