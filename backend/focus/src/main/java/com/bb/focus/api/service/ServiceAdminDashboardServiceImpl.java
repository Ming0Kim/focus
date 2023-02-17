package com.bb.focus.api.service;

import com.bb.focus.api.response.ProceedingProcessRes;
import com.bb.focus.db.repository.ServiceAdminDashboardCustomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceAdminDashboardServiceImpl implements ServiceAdminDashboardService {

  private final ServiceAdminDashboardCustomRepository serviceAdminDashboardCustomRepository;

  public List<ProceedingProcessRes> findProceedingCompanys() {

    return serviceAdminDashboardCustomRepository.findAllProceedingCompany();

  }

  public Long getProceedingCompanysCount() {

   return serviceAdminDashboardCustomRepository.getCountProceedingCompany();

  }
}
