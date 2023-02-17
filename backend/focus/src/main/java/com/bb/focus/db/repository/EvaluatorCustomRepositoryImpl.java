package com.bb.focus.db.repository;

import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.common.util.QueryDslUtil;
import com.bb.focus.db.entity.company.QCompanyAdmin;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.evaluator.QEvaluator;
import com.bb.focus.db.entity.helper.QEvaluatorInterviewRoom;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.entity.interview.QInterviewRoom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
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
public class EvaluatorCustomRepositoryImpl implements EvaluatorCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  QEvaluator qEvaluator = QEvaluator.evaluator;
  QCompanyAdmin qCompanyAdmin = QCompanyAdmin.companyAdmin;
  QInterviewRoom qInterviewRoom = QInterviewRoom.interviewRoom;
  QEvaluatorInterviewRoom qEvaluatorInterviewRoom = QEvaluatorInterviewRoom.evaluatorInterviewRoom;

  /**
   * 페이징 사용 + 기업관리자 아이디로 모든 평가자 조회
   */
  public Page<EvaluatorRes> findAllEvaluatorsByCompanyAdminIdUsePaging(Pageable pageable,
      String search, Long companyAdminId) {

    List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

    List<EvaluatorRes> results = jpaQueryFactory
        .select(Projections.constructor(EvaluatorRes.class,
            qEvaluator.id,
            qEvaluator.name,
            qEvaluator.code,
            qEvaluator.department,
            qEvaluator.position,
            qEvaluator.image,
            qEvaluator.userId,
            qEvaluator.tel,
            qEvaluator.email))
        .from(qEvaluator)
        .where(
            eqCompanyAdminId(companyAdminId),
            containName(search)
        )
        .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    long totalCount = jpaQueryFactory
            .select(qEvaluator.count())
            .from(qEvaluator)
            .where(
                eqCompanyAdminId(companyAdminId),
                containName(search)
            )
            .fetchOne();

    return new PageImpl<>(results, pageable, totalCount);
  }


  /**
   * 기업 관리자 시퀀스넘버로 모든 평가자 조회
   */
  public List<Evaluator> findAllEvaluatorsByCompanyAdminId(Long companyAdminId) {
    return jpaQueryFactory
        .selectFrom(qEvaluator)
//        .join(qEvaluator.companyAdmin, qCompanyAdmin)
        .where(eqCompanyAdminId(companyAdminId))
        .fetch();
  }

  @Override
  public Evaluator findEvaluatorByUserId(String userId) {
    Evaluator evaluator = jpaQueryFactory.select(qEvaluator).from(qEvaluator)
        .where(qEvaluator.userId.eq(userId)).fetchOne();
    return evaluator;
  }

  @Override
  public Evaluator findEvaluatorById(Long id) {
    Evaluator evaluator = jpaQueryFactory.select(qEvaluator).from(qEvaluator)
        .where(qEvaluator.id.eq(id)).fetchOne();
    return evaluator;
  }

  @Override
  public List<String> findAllDepartmentsByCompanyAdminId(Long companyAdminId) {
    return jpaQueryFactory
        .select(qEvaluator.department)
        .from(qEvaluator)
        .where(eqCompanyAdminId(companyAdminId))
        .distinct()
        .fetch();
  }

  @Override
  public Page<EvaluatorRes> findDepartmentEvaluators(Pageable pageable, List<String> departmentList,
      Long companyAdminId) {

    List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

    List<EvaluatorRes> results = jpaQueryFactory
        .select(Projections.constructor(EvaluatorRes.class,
            qEvaluator.id,
            qEvaluator.name,
            qEvaluator.code,
            qEvaluator.department,
            qEvaluator.position,
            qEvaluator.image,
            qEvaluator.userId,
            qEvaluator.tel,
            qEvaluator.email))
        .from(qEvaluator)
        .where(
            eqCompanyAdminId(companyAdminId),
            qEvaluator.department.in(departmentList)
        )
        .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    long totalCount = jpaQueryFactory
            .select(qEvaluator.count())
            .from(qEvaluator)
            .where(
                    eqCompanyAdminId(companyAdminId),
                    qEvaluator.department.in(departmentList)
            )
            .fetchOne();

    return new PageImpl<>(results, pageable, totalCount);
  }

  @Override
  public List<InterviewRoomRes> findInterviewRoomsById(Long id) {

    List<InterviewRoomRes> resultList = null;

    return resultList;
  }

  private BooleanExpression eqEvaluatorId(Long evaluatorId) {
    if(evaluatorId.equals(null)){
      return null;
    }
    return qEvaluator.id.eq(evaluatorId);
  }

  private BooleanExpression eqCompanyAdminId(Long companyAdminId) {

    if (companyAdminId.equals(null)) {
      return null;
    }
    return qCompanyAdmin.id.eq(companyAdminId);
  }

  private BooleanExpression containName(String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }
    return qEvaluator.name.containsIgnoreCase(name);
  }

  private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
    List<OrderSpecifier> ORDERS = new ArrayList<>();

    if (pageable.getSort() != null) {
      for (Sort.Order order : pageable.getSort()) {
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

        switch (order.getProperty()) {
          case "code":
            OrderSpecifier<?> code = QueryDslUtil.getSortedColumn(direction, QEvaluator.evaluator,
                "code");
            ORDERS.add(code);
          case "name":
            OrderSpecifier<?> name = QueryDslUtil.getSortedColumn(direction, QEvaluator.evaluator,
                "name");
            ORDERS.add(name);
          case "department":
            OrderSpecifier<?> department = QueryDslUtil.getSortedColumn(direction,
                QEvaluator.evaluator, "department");
            ORDERS.add(department);
            break;
          default:
            break;
        }
      }
    }
    return ORDERS;
  }
}
