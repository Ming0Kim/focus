package com.bb.focus.db.repository;

import com.bb.focus.db.entity.evaluation.EvaluationResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationResultRepository extends JpaRepository<EvaluationResult,Long> {
  List<EvaluationResult> findByapplicantEvaluatorId(Long applicantEvaluatorId);
}
