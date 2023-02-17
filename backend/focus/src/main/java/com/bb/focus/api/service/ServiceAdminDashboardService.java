package com.bb.focus.api.service;

import com.bb.focus.api.response.ProceedingProcessRes;
import java.util.List;

public interface ServiceAdminDashboardService {

  List<ProceedingProcessRes> findProceedingCompanys();

  Long getProceedingCompanysCount();

}
