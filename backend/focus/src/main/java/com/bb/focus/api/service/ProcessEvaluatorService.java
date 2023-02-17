package com.bb.focus.api.service;

public interface ProcessEvaluatorService {

  public void saveProcessEvaluator(Long processId, Long evaluatorId);

  public void removeProcessEvaluator(Long processId, Long evaluatorId);

}
