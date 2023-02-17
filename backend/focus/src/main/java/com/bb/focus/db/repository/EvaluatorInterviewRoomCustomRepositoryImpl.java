package com.bb.focus.db.repository;

import com.bb.focus.db.entity.evaluator.QEvaluator;
import com.bb.focus.db.entity.helper.QEvaluatorInterviewRoom;
import com.bb.focus.db.entity.interview.QInterviewRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EvaluatorInterviewRoomCustomRepositoryImpl implements EvaluatorInterviewRoomCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  QEvaluatorInterviewRoom qEvaluatorInterviewRoom = QEvaluatorInterviewRoom.evaluatorInterviewRoom;
  QInterviewRoom qInterviewRoom = QInterviewRoom.interviewRoom;
  QEvaluator qEvaluator = QEvaluator.evaluator;

  @Override
  public void deleteByInterviewRoomIdAndEvaluatorId(Long interviewRoomId, Long evaluatorId) {
    jpaQueryFactory
        .delete(qEvaluatorInterviewRoom)
        .where(
            eqInterviewRoomId(interviewRoomId),
            eqEvaluatorId(evaluatorId)
        )
        .execute();

  }

  private BooleanExpression eqInterviewRoomId(Long interviewRoomId) {
    if(interviewRoomId.equals(null)){
      return null;
    }
    return qInterviewRoom.id.eq(interviewRoomId);
  }

  private BooleanExpression eqEvaluatorId(Long evaluatorId) {
    if(evaluatorId.equals(null)){
      return null;
    }
    return qEvaluator.id.eq(evaluatorId);
  }
}
