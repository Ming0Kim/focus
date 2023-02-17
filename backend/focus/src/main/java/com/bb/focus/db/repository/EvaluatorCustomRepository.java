package com.bb.focus.db.repository;

import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.db.entity.evaluator.Evaluator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EvaluatorCustomRepository {

  Page<EvaluatorRes> findAllEvaluatorsByCompanyAdminIdUsePaging(Pageable pageable, String search, Long companyAdminId);

  List<Evaluator> findAllEvaluatorsByCompanyAdminId(Long companyAdminId);

  Evaluator findEvaluatorByUserId(String userId);

  Evaluator findEvaluatorById(Long id);

  List<String> findAllDepartmentsByCompanyAdminId(Long companyAdminId);

  Page<EvaluatorRes> findDepartmentEvaluators(Pageable pageable, List<String> departmentList, Long companyAdminId);

  List<InterviewRoomRes> findInterviewRoomsById(Long id);
}
