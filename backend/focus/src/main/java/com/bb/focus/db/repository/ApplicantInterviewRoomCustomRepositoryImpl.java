package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.QApplicant;
import com.bb.focus.db.entity.helper.QApplicantInterviewRoom;
import com.bb.focus.db.entity.helper.QEvaluatorInterviewRoom;
import com.bb.focus.db.entity.interview.QInterviewRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplicantInterviewRoomCustomRepositoryImpl implements ApplicantInterviewRoomCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  QApplicantInterviewRoom qApplicantInterviewRoom = QApplicantInterviewRoom.applicantInterviewRoom;
  QInterviewRoom qInterviewRoom = QInterviewRoom.interviewRoom;
  QApplicant qApplicant = QApplicant.applicant;

  public void deleteByInterviewRoomIdAndApplicantId(Long interviewRoomId, Long applicantId) {
    jpaQueryFactory
        .delete(qApplicantInterviewRoom)
        .where(
            eqInterviewRoomId(interviewRoomId),
            eqApplicantId(applicantId)
        )
        .execute();
  }
  private BooleanExpression eqInterviewRoomId(Long interviewRoomId) {
    if(interviewRoomId.equals(null)){
      return null;
    }
    return qInterviewRoom.id.eq(interviewRoomId);
  }

  private BooleanExpression eqApplicantId(Long applicantId) {
    if(applicantId.equals(null)){
      return null;
    }
    return qApplicant.id.eq(applicantId);
  }
}
