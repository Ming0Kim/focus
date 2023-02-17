package com.bb.focus.db.repository;

import com.bb.focus.db.entity.company.CompanyAdmin;
import java.util.List;

public interface CompanyAdminCustomRepository {

  CompanyAdmin findCompanyAdminByUserId(String userId);
  CompanyAdmin findCompanyAdminById(Long id);

  List<CompanyAdmin> findAll();

  Long updateCompanyAdminByUserId(CompanyAdmin companyAdmin);

  List<CompanyAdmin> findAllContainsToday();

}
