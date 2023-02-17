package com.bb.focus.db.repository;

import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomInfoRes;
import com.bb.focus.common.util.QueryDslUtil;
import com.bb.focus.db.entity.company.QCompanyAdmin;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.evaluator.QEvaluator;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.QInterview;
import com.bb.focus.db.entity.process.QProcess;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InterviewCustomRepositoryImpl implements InterviewCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  QInterview qInterview = QInterview.interview;
  QProcess qProcess = QProcess.process;

  @Override
  public Interview findInterviewById(Long id) {
    Interview interview = jpaQueryFactory.select(qInterview).from(qInterview)
        .where(qInterview.id.eq(id)).fetchOne();
    return interview;
  }

  @Override
  public List<InterviewRoomInfoRes> findInterviewRoomIds(Long processId) {

    List<InterviewRoomInfoRes> results = jpaQueryFactory
        .select(Projections.constructor(InterviewRoomInfoRes.class,
            qInterview.id,
            qInterview.step
        ))
        .from(qInterview)
        .where(eqProcessId(processId))
        .fetch();

    return results;
  }

  private BooleanExpression eqProcessId(Long processId){
    if(processId.equals(null)){
      return null;
    }
    return qProcess.id.eq(processId);
  }

}
