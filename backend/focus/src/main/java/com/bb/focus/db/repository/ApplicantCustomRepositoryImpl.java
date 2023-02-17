package com.bb.focus.db.repository;

import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.common.util.QueryDslUtil;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.applicant.QApplicant;
import com.bb.focus.db.entity.company.QCompanyAdmin;
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
public class ApplicantCustomRepositoryImpl implements ApplicantCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  QCompanyAdmin qCompanyAdmin = QCompanyAdmin.companyAdmin;
  QApplicant qApplicant = QApplicant.applicant;
  QProcess qProcess = QProcess.process;

  public List<Applicant> findAllApplicantsByCompanyAdminId(Long companyAdminId) {
    return jpaQueryFactory
        .selectFrom(qApplicant)
        .join(qApplicant.companyAdmin, qCompanyAdmin)
        .where(eqCompanyAdminId(companyAdminId))
        .fetch();
  }

  @Override
  public Applicant findApplicantByUserId(String userId) {
    Applicant applicant = jpaQueryFactory.select(qApplicant).from(qApplicant)
        .where(qApplicant.userId.eq(userId)).fetchOne();
    return applicant;
  }

  @Override
  public Applicant findApplicantById(Long id) {
    Applicant applicant = jpaQueryFactory.select(qApplicant).from(qApplicant)
        .where(qApplicant.id.eq(id)).fetchOne();
    return applicant;
  }

  @Override
  public Page<ApplicantRes> findAllApplicantsWithPaging(Pageable pageable, String search, Long companyAdminId, Long processId) {

    List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

    List<ApplicantRes> results = jpaQueryFactory
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
            .from(qApplicant)
            .where(eqCompanyAdminId(companyAdminId),
                    eqProcessId(processId),
                    containName(search)
            )
            .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    long totalCount = jpaQueryFactory
            .select(qApplicant.count())
            .from(qApplicant)
            .where(
                    eqCompanyAdminId(companyAdminId),
                    eqProcessId(processId),
                    containName(search)
            )
            .fetchOne();

    return new PageImpl<>(results, pageable, totalCount);

  }

  private BooleanExpression eqCompanyAdminId(Long companyAdminId) {
    if (companyAdminId.equals(null)) {
      return null;
    }
    return qCompanyAdmin.id.eq(companyAdminId);
  }

  private BooleanExpression eqProcessId(Long processId){
    if(processId.equals(null)){
      return null;
    }
    return qProcess.id.eq(processId);
  }

  private BooleanExpression containName(String name){
    if(name == null || name.isEmpty()){
      return null;
    }
    return qApplicant.name.containsIgnoreCase(name);
  }

  private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable){
    List<OrderSpecifier> ORDERS = new ArrayList<>();

    if(pageable.getSort() != null){
      for(Sort.Order order : pageable.getSort()){
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

        switch(order.getProperty()){
          case "code":
            OrderSpecifier<?> code = QueryDslUtil.getSortedColumn(direction, QApplicant.applicant, "code");
            ORDERS.add(code);
          case "name":
            OrderSpecifier<?> name = QueryDslUtil.getSortedColumn(direction, QApplicant.applicant, "name");
            ORDERS.add(name);
          default:
            break;
        }
      }
    }
    return ORDERS;
  }
}
