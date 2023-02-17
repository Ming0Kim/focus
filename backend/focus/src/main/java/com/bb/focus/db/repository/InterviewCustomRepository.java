package com.bb.focus.db.repository;

import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomInfoRes;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.interview.Interview;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterviewCustomRepository {

  Interview findInterviewById(Long id);
  List<InterviewRoomInfoRes> findInterviewRoomIds(Long processId);

}
