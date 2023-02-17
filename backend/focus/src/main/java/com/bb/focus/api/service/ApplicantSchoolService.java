package com.bb.focus.api.service;

import com.bb.focus.db.entity.admin.Faq;
import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApplicantSchoolService {

  List<ApplicantCollege> findAllColleges();

  List<ApplicantUniv> findAllUnivs();

  List<ApplicantGraduate> findAllGraduates();

}
