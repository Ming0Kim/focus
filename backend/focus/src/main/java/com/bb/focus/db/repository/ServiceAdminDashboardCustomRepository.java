package com.bb.focus.db.repository;

import com.bb.focus.api.response.ProceedingProcessRes;
import java.util.List;

public interface ServiceAdminDashboardCustomRepository {

  public List<ProceedingProcessRes> findAllProceedingCompany();

  public Long getCountProceedingCompany();
}
