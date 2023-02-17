package com.bb.focus.api.service;

import com.bb.focus.api.request.AddRemoveApplicantReq;
import com.bb.focus.api.request.AddRemoveEvaluatorReq;

public interface ApplicantEvaluatorService {

  void mappingEvaluatorWithApplicants(AddRemoveEvaluatorReq addRemoveEvaluatorReq);

  void mappingApplicantWithEvaluators(AddRemoveApplicantReq addRemoveApplicantReq);
}
