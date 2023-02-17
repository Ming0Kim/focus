package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.Faq;
import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import com.bb.focus.db.repository.ApplicantCollegeRepository;
import com.bb.focus.db.repository.ApplicantGraduateRepository;
import com.bb.focus.db.repository.ApplicantUnivRepository;
import com.bb.focus.db.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("applicantSchoolService")
public class ApplicantSchoolServiceImpl implements ApplicantSchoolService {

  @Autowired
  ApplicantCollegeRepository applicantCollegeRepository;

  @Autowired
  ApplicantUnivRepository applicantUnivRepository;

  @Autowired
  ApplicantGraduateRepository applicantGraduateRepository;



  @Override
  public List<ApplicantCollege> findAllColleges() {
    return applicantCollegeRepository.findAll();
  }

  @Override
  public List<ApplicantUniv> findAllUnivs() {
    return applicantUnivRepository.findAll();
  }

  @Override
  public List<ApplicantGraduate> findAllGraduates() {
    return applicantGraduateRepository.findAll();
  }
}
