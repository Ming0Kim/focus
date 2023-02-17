package com.bb.focus.db.repository;

import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.db.entity.applicant.QApplicant;
import com.bb.focus.db.entity.evaluator.QEvaluator;
import com.bb.focus.db.entity.helper.QApplicantEvaluator;
import com.bb.focus.db.entity.interview.QInterviewRoom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplicantEvaluatorCustomRepositoryImpl implements ApplicantEvaluatorCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;
  QInterviewRoom qInterviewRoom = QInterviewRoom.interviewRoom;
  QEvaluator qEvaluator = QEvaluator.evaluator;
  QApplicantEvaluator qApplicantEvaluator = QApplicantEvaluator.applicantEvaluator;
  QApplicant qApplicant = QApplicant.applicant;

  @Override
  public void deleteByInterviewRoomIdAndEvaluatorId(Long interviewRoomId, Long evaluatorId) {
    jpaQueryFactory
            .delete(qApplicantEvaluator)
            .where(
                    eqInterviewRoomId(interviewRoomId),
                    eqEvaluatorId(evaluatorId)
            )
            .execute();

  }

  @Override
  public void deleteByInterviewRoomIdAndApplicantId(Long interviewRoomId, Long applicantId) {
    jpaQueryFactory
            .delete(qApplicantEvaluator)
            .where(
                    eqInterviewRoomId(interviewRoomId),
                    eqApplicantId(applicantId)
            )
            .execute();

  }

  public List<Long> findApplicantIds(Long interviewRoomId) {

    return jpaQueryFactory
        .select(qApplicant.id)
        .from(qApplicantEvaluator)
        .where(eqInterviewRoomId(interviewRoomId))
        .distinct().fetch();
  }

  public List<Long> findEvaluatorIds(Long interviewRoomId) {

    return jpaQueryFactory
        .select(qEvaluator.id)
        .from(qApplicantEvaluator)
        .where(eqInterviewRoomId(interviewRoomId))
        .distinct().fetch();
  }

  @Override
  public List<ApplicantRes> findAttendingApplicants(Long interviewRoomId) {

    return jpaQueryFactory
        .select(Projections.constructor(ApplicantRes.class,
            qApplicant.id,
            qApplicant.name,
            qApplicant.userId,
            qApplicant.code,
            qApplicant.image,
            qApplicant.tel,
            qApplicant.email,
            qApplicant.major,
            qApplicant.totalCredit,
            qApplicant.credit
        ))
        .distinct()
        .from(qApplicantEvaluator)
        .innerJoin(qApplicant).on(qApplicant.id.eq(qApplicantEvaluator.applicant.id))
        .where(qApplicantEvaluator.interviewRoom.id.eq(interviewRoomId))
        .orderBy(qApplicant.code.asc())
        .fetch();
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

  private BooleanExpression eqApplicantId(Long applicantId) {
    if(applicantId.equals(null)){
      return null;
    }
    return qApplicant.id.eq(applicantId);
  }
}
