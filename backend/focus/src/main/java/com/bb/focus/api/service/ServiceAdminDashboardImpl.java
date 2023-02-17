package com.bb.focus.api.service;

import com.bb.focus.api.response.ProceedingCompanyRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceAdminDashboardImpl implements ServiceAdminDashboard {

  public List<ProceedingCompanyRes> findProceedingCompanys() {
    return null;
  }
}
