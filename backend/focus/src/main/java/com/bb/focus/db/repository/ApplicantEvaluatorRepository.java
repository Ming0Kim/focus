package com.bb.focus.db.repository;

import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantEvaluatorRepository extends JpaRepository<ApplicantEvaluator,Long>, ApplicantEvaluatorCustomRepository {
    List<ApplicantEvaluator> findByEvaluatorId(Long evaluatorId);


    ApplicantEvaluator findByEvaluatorIdAndApplicantIdAndInterviewId(Long evaluatorId,Long applicantId,Long interviewId);

}
