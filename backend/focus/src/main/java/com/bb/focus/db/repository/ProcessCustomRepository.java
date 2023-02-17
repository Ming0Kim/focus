package com.bb.focus.db.repository;

import com.bb.focus.api.response.ProcessDetailRes;
import com.bb.focus.api.response.ProcessRes;
import java.util.List;

public interface ProcessCustomRepository {

  List<ProcessRes> findAllExpectedProcess(Long companyAdminId);

  ProcessDetailRes findProcessDetail(Long processId);

  int findProcessGoingOn();

}
