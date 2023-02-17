package com.bb.focus.db.repository;

import com.bb.focus.db.entity.helper.EvaluatorInterviewRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluatorInterviewRoomRepository extends JpaRepository<EvaluatorInterviewRoom, Long>, EvaluatorInterviewRoomCustomRepository{



}
