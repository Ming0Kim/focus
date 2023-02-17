package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationSheetRepository extends JpaRepository<EvaluationSheet, Long> {
    Optional<EvaluationSheet> findById(Long evaluationSheetId);
    List<EvaluationSheet> findAllByCompanyAdminId(Long companyAdminId);

}
