package com.bb.focus.api.service;

import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.helper.ProcessEvaluator;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.repository.EvaluatorRepository;
import com.bb.focus.db.repository.ProcessEvaluatorRepository;
import com.bb.focus.db.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProcessEvaluatorServiceImpl implements ProcessEvaluatorService{

  private final ProcessEvaluatorRepository processEvaluatorRepository;
  private final ProcessRepository processRepository;
  private final EvaluatorRepository evaluatorRepository;

  @Transactional
  public void saveProcessEvaluator(Long processId, Long evaluatorId) {

    Process process = processRepository.findById(processId).orElseThrow(IllegalArgumentException::new);
    Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow(IllegalArgumentException::new);

    ProcessEvaluator processEvaluator = new ProcessEvaluator();
    processEvaluator.setProcess(process);
    processEvaluator.setEvaluator(evaluator);

    processEvaluatorRepository.save(processEvaluator);
  }

  @Transactional
  public void removeProcessEvaluator(Long processId, Long evaluatorId) {

    processEvaluatorRepository.deleteByProcessIdAndEvaluatorId(processId, evaluatorId);


  }
}
