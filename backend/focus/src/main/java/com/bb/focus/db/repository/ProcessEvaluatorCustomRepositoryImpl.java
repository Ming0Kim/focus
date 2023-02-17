package com.bb.focus.db.repository;

import com.bb.focus.db.entity.evaluator.QEvaluator;
import com.bb.focus.db.entity.helper.QProcessEvaluator;
import com.bb.focus.db.entity.process.QProcess;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProcessEvaluatorCustomRepositoryImpl implements ProcessEvaluatorCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  QProcessEvaluator qProcessEvaluator = QProcessEvaluator.processEvaluator;
  QProcess qProcess = QProcess.process;
  QEvaluator qEvaluator = QEvaluator.evaluator;

  public void deleteByProcessIdAndEvaluatorId(Long processId, Long evaluatorId) {
    jpaQueryFactory
        .delete(qProcessEvaluator)
        .where(
            eqProcessId(processId),
            eqEvaluatorId(evaluatorId)
        )
        .execute();
  }

  private BooleanExpression eqProcessId(Long processId) {
    if(processId.equals(null)){
      return null;
    }
    return qProcess.id.eq(processId);
  }

  private BooleanExpression eqEvaluatorId(Long evaluatorId) {
    if(evaluatorId.equals(null)){
      return null;
    }
    return qEvaluator.id.eq(evaluatorId);
  }

}
