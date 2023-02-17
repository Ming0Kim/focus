package com.bb.focus.db.repository;

import com.bb.focus.db.entity.company.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Long>,
    CompanyAdminCustomRepository {


}
