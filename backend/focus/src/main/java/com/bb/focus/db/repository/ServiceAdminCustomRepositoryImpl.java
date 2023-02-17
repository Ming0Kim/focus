package com.bb.focus.db.repository;

import com.bb.focus.db.entity.admin.QServiceAdmin;
import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceAdminCustomRepositoryImpl implements ServiceAdminCustomRepository {

  @Autowired
  private JPAQueryFactory jpaQueryFactory;
  QServiceAdmin qServiceAdmin = QServiceAdmin.serviceAdmin;

  @Override
  public ServiceAdmin findServiceAdminByUserId(String userId) {
    ServiceAdmin serviceAdmin = jpaQueryFactory.select(qServiceAdmin).from(qServiceAdmin)
        .where(qServiceAdmin.userId.eq(userId)).fetchOne();
    return serviceAdmin;
  }

  @Override
  public ServiceAdmin findServiceAdminById(Long id) {
    ServiceAdmin serviceAdmin = jpaQueryFactory.select(qServiceAdmin).from(qServiceAdmin)
        .where(qServiceAdmin.id.eq(id)).fetchOne();
    return serviceAdmin;
  }
}
