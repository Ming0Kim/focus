package com.bb.focus.db.repository;

public interface EvaluatorInterviewRoomCustomRepository {

  void deleteByInterviewRoomIdAndEvaluatorId(Long interviewRoomId, Long evaluatorId);

}
