package com.bb.focus.api.service;

import com.bb.focus.api.request.CompanyAdminRegisterPostReq;
import com.bb.focus.api.response.ApplicantLogRes;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.api.response.ProcessRes;
import com.bb.focus.db.entity.company.CompanyAdmin;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyAdminService {
    CompanyAdmin createCompanyAdmin(CompanyAdminRegisterPostReq userRegisterInfo);

    CompanyAdmin updateCompanyAdminUserId(CompanyAdmin companyAdminRevised);

    CompanyAdmin getCompanyAdminByUserId(String userId);
    CompanyAdmin getCompanyAdminById(Long id);

    List<CompanyAdmin> getAll();

    Long updateCompanyAdminByUserInfo(CompanyAdminRegisterPostReq userUpdateInfo);
    int getAllEvaluationCount(Long companyAdminId);
    int getAllApplicantCount(Long companyAdminId);
    List<InterviewRoomRes> getAllReservedInterview(Long processId);
    List<ProcessRes> getAllProcess(Long companyAdminId);
    List<ApplicantLogRes> getAllInterviewPerPassApplicant(Long processId);

    List<ProcessRes> getFinishStepPerProcessInfo(Long companyId);
    List<CompanyAdmin> getAllContainsToday();
}
