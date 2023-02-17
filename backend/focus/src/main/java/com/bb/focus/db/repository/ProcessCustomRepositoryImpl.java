package com.bb.focus.db.repository;

import com.bb.focus.api.response.ProcessDetailRes;
import com.bb.focus.api.response.ProcessRes;
import com.bb.focus.db.entity.company.QCompanyAdmin;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.entity.process.QProcess;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProcessCustomRepositoryImpl implements ProcessCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  QProcess qProcess = QProcess.process;
  QCompanyAdmin qCompanyAdmin = QCompanyAdmin.companyAdmin;

  public List<ProcessRes> findAllExpectedProcess(Long companyAdminId) {
    LocalDateTime present = LocalDateTime.now();

    List<ProcessRes> results = jpaQueryFactory
        .select(Projections.constructor(ProcessRes.class,
            qProcess.id,
            qProcess.name,
            qProcess.startDate,
            qProcess.endDate,
            qProcess.interviewCount,
            qProcess.currentStep))
        .from(qProcess)
        .where(eqCompanyAdminId(companyAdminId),
            qProcess.endDate.gt(present)
        )
        .orderBy(qProcess.startDate.asc())
        .fetch();

    return results;
  }

  @Override
  public ProcessDetailRes findProcessDetail(Long processId) {
    ProcessDetailRes result = jpaQueryFactory
        .select(Projections.constructor(ProcessDetailRes.class,
            qProcess.id,
            qProcess.name,
            qProcess.startDate,
            qProcess.endDate,
            qProcess.interviewCount,
            qProcess.currentStep))
        .from(qProcess)
        .where(qProcess.id.eq(processId))
        .fetchOne();
    return result;
  }

  public int findProcessGoingOn(){
    List<Process> result = jpaQueryFactory
        .select(qProcess)
        .from(qProcess)
        .where(qProcess.startDate.lt(LocalDateTime.now()),
            qProcess.endDate.gt(LocalDateTime.now()))
        .fetch();
    return result.size();
  }

  private BooleanExpression eqCompanyAdminId(Long companyAdminId) {
    if (companyAdminId.equals(null)) {
      return null;
    }
    return qCompanyAdmin.id.eq(companyAdminId);
  }

}
