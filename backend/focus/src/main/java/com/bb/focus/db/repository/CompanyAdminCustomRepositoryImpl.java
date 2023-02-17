package com.bb.focus.db.repository;

import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.company.QCompanyAdmin;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyAdminCustomRepositoryImpl implements CompanyAdminCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCompanyAdmin qCompanyAdmin = QCompanyAdmin.companyAdmin;

    @Override
    public CompanyAdmin findCompanyAdminByUserId(String userId) {
        CompanyAdmin companyAdmin = jpaQueryFactory.select(qCompanyAdmin).from(qCompanyAdmin)
                .where(qCompanyAdmin.userId.eq(userId)).fetchOne();
        return companyAdmin;
    }

    @Override
    public CompanyAdmin findCompanyAdminById(Long id) {
        CompanyAdmin companyAdmin = jpaQueryFactory.select(qCompanyAdmin).from(qCompanyAdmin)
                .where(qCompanyAdmin.id.eq(id)).fetchOne();
        return companyAdmin;
    }

    @Override
    public List<CompanyAdmin> findAll() {
        List<CompanyAdmin> companyAdminList = jpaQueryFactory.select(qCompanyAdmin).from(qCompanyAdmin)
                .fetch();
        if (companyAdminList == null) {
            return null;
        }
        return companyAdminList;
    }

    @Override
    public Long updateCompanyAdminByUserId(CompanyAdmin companyAdmin) {
        Long companyAdminId = jpaQueryFactory.update(qCompanyAdmin)
                .where(qCompanyAdmin.userId.eq(companyAdmin.getUserId()))
                .set(qCompanyAdmin.userId, companyAdmin.getUserId())
                .set(qCompanyAdmin.companyName, companyAdmin.getCompanyName())
                .set(qCompanyAdmin.userId, companyAdmin.getUserId())
                .set(qCompanyAdmin.email, companyAdmin.getEmail())
                .set(qCompanyAdmin.startDate, companyAdmin.getStartDate())
                .set(qCompanyAdmin.endDate, companyAdmin.getEndDate())
                .set(qCompanyAdmin.industry, companyAdmin.getIndustry())
                .set(qCompanyAdmin.logoImage, companyAdmin.getLogoImage())
                .set(qCompanyAdmin.name, companyAdmin.getName())
                .set(qCompanyAdmin.skinColor, companyAdmin.getSkinColor())
                .execute();

        return companyAdminId;
    }

    @Override
    public List<CompanyAdmin> findAllContainsToday() {
        return jpaQueryFactory
                .select(qCompanyAdmin)
                .from(qCompanyAdmin)
                .where(qCompanyAdmin.startDate.lt(LocalDateTime.now()), qCompanyAdmin.endDate.gt(LocalDateTime.now()))
                .orderBy(qCompanyAdmin.endDate.asc())
                .fetch();

    }


}
