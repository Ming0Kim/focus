package com.bb.focus.db.repository;

import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>, InterviewCustomRepository {
}
