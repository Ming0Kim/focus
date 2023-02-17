package com.bb.focus.db.repository;

import com.bb.focus.api.response.ProceedingProcessRes;
import com.bb.focus.db.entity.company.QCompanyAdmin;
import com.bb.focus.db.entity.process.QProcess;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ServiceAdminDashboardCustomRepositoryImpl implements ServiceAdminDashboardCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;
  QCompanyAdmin qCompanyAdmin = QCompanyAdmin.companyAdmin;
  QProcess qProcess = QProcess.process;

  public List<ProceedingProcessRes> findAllProceedingCompany() {

    LocalDateTime current = LocalDateTime.now();

    return jpaQueryFactory
        .select(Projections.constructor(ProceedingProcessRes.class,
            qCompanyAdmin.companyName,
            qProcess.name,
            qProcess.startDate,
            qProcess.endDate
            ))
        .from(qProcess)
        .innerJoin(qCompanyAdmin).on(qProcess.companyAdmin.eq(qCompanyAdmin))
        .where(qProcess.startDate.lt(current), qProcess.endDate.gt(current))
        .fetch();
  }

  public Long getCountProceedingCompany() {

    LocalDateTime current = LocalDateTime.now();

    return jpaQueryFactory
        .select(qCompanyAdmin.countDistinct())
        .from(qProcess)
        .innerJoin(qCompanyAdmin).on(qProcess.companyAdmin.eq(qCompanyAdmin))
        .where(qProcess.startDate.lt(current), qProcess.endDate.gt(current))
        .fetchOne();
  }
}
