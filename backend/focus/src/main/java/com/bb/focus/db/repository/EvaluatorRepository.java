package com.bb.focus.db.repository;

import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.db.entity.evaluator.Evaluator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluatorRepository extends JpaRepository<Evaluator, Long>, EvaluatorCustomRepository {

  Optional<Evaluator> findAllByEmail(String email);

}
