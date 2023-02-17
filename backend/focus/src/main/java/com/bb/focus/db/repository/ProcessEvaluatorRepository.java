package com.bb.focus.db.repository;

import com.bb.focus.db.entity.helper.ProcessEvaluator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessEvaluatorRepository extends JpaRepository<ProcessEvaluator, Long>, ProcessEvaluatorCustomRepository {

}
