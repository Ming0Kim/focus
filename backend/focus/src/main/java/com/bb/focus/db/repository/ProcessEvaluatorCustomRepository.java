package com.bb.focus.db.repository;

public interface ProcessEvaluatorCustomRepository {

  void deleteByProcessIdAndEvaluatorId(Long processId, Long evaluatorId);

}
