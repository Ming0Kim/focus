package com.bb.focus.api.service;

import com.bb.focus.api.request.EvaluatorInfoReq;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.db.entity.evaluator.Evaluator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;

public interface EvaluatorService {

  //평가자 계정 생성
  public Long create(Long companyAdminId, EvaluatorInfoReq evaluatorInfoReq);

  //ID, PWD 자동생성
  public void autoAssignAccount(Long id) throws MessagingException;

  //평가자 계정 기본정보 수정 (이름, 사번, 부서, 직급, 전화번호, 이메일, 사진)
  public void updateEvaluatorInfo(Long id, EvaluatorInfoReq evaluatorInfoReq);

  public void removeEvaluator(Long id);

  public Page<EvaluatorRes> findAllEvaluatorsUsePaging(Pageable pageable, String search, Long companyAdminId);

  public List<Evaluator> findAllEvaluators(Long companyAdminId);

  public Evaluator findEvaluator(Long id);

  public Evaluator getEvaluatorByUserId(String userId);

  public Evaluator getEvaluatorById(Long id);

  public List<String> getDepartments(Long companyAdminId);

  Page<EvaluatorRes> findDepartmentEvaluators(Pageable pageable, List<String> departmentList, Long companyAdminId);

  List<InterviewRoomRes> getInterviewRoomsById(Long id);
}
