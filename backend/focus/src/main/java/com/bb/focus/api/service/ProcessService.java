package com.bb.focus.api.service;

import com.bb.focus.api.request.ProcessReq;
import com.bb.focus.api.response.ProcessDetailRes;
import com.bb.focus.api.response.ProcessRes;
import java.util.List;

public interface ProcessService {

  public Long createProcess(ProcessReq processReq, Long companyAdminId);

  public Long updateProcess(Long id, ProcessReq processReq);

  public void removeProcess(Long id);

  public List<ProcessRes> getAllExpectedProcess(Long companyAdminId);

  public ProcessDetailRes getProcessDetail(Long processId);

  public int getProcessGoingOnNumber();

  List<ProcessRes> getAllPassedProcess(Long companyAdminId);
}
