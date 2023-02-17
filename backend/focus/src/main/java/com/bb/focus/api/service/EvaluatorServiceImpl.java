package com.bb.focus.api.service;

import com.bb.focus.api.request.EvaluatorInfoReq;
import com.bb.focus.api.response.EvaluatorRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.repository.CompanyAdminRepository;
import com.bb.focus.db.repository.EvaluatorRepository;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService{

  private final CompanyAdminRepository companyAdminRepository;

  private final EvaluatorRepository evaluatorRepository;

  private final MailService mailService;
//  private final PasswordEncoder passwordEncoder;

  /**
   * 평가자 계정 생성
   */
  @Transactional
  public Long create(Long companyAdminId, EvaluatorInfoReq evaluatorInfo) {

    Evaluator evaluator = new Evaluator();

    evaluator.setName(evaluatorInfo.getName());
    evaluator.setCode(evaluatorInfo.getCode());
    evaluator.setDepartment(evaluatorInfo.getDepartment());
    evaluator.setPosition(evaluatorInfo.getPosition());
    evaluator.setTel(evaluatorInfo.getTel());
    evaluator.setEmail(evaluatorInfo.getEmail());

    validateDuplicateEvaluator(evaluator);

    CompanyAdmin companyAdmin = companyAdminRepository.findById(companyAdminId).orElseThrow(IllegalArgumentException::new);

    evaluator.setExpireDate(companyAdmin.getEndDate());

    companyAdmin.addEvaluator(evaluator);

    evaluatorRepository.save(evaluator);
    return evaluator.getId();
  }

  /**
   * 평가자 id, pwd 자동 생성 및 할당
   * 생성 규칙 ] 아이디 : 기업이름 + E + 평가자사번
   *           비밀번호 : 랜덤 생성 문자열
   */
  @Transactional
  public void autoAssignAccount(Long id) throws MessagingException {

    Evaluator evaluator = evaluatorRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    String newId = evaluator.getCompanyAdmin().getCompanyName() + "E" + evaluator.getCode();
    String newPwd = getRandomString();

    //메일
    Map<String, String> content = new HashMap<>();
    content.put("id", newId);
    content.put("pwd", newPwd);
    mailService.sendAccountMail(evaluator.getEmail(), content);

    //암호화
//    String encodedPwd = EncryptionUtils.encryptSHA256(newPwd);
    String encodedPwd = newPwd;

    evaluator.setUserId(newId);
    evaluator.setPwd(encodedPwd);
  }

  /**
   * 평가자의 기본 정보를 수정한다.
   * 수정 항목 ] 이름, 사번, 부서, 직급, 전화번호, 이메일, 사진
   */
  @Transactional
  public void updateEvaluatorInfo(Long id, EvaluatorInfoReq evaluatorInfo) {
    Evaluator evaluator = evaluatorRepository.findById(id).orElseThrow(IllegalAccessError::new);

    evaluator.setName(evaluatorInfo.getName());
    evaluator.setCode(evaluatorInfo.getCode());
    evaluator.setDepartment(evaluatorInfo.getDepartment());
    evaluator.setPosition(evaluatorInfo.getPosition());
    evaluator.setTel(evaluatorInfo.getTel());
    evaluator.setEmail(evaluatorInfo.getEmail());
  }

  @Transactional
  public void removeEvaluator(Long id) {
    evaluatorRepository.deleteById(id);
  }

  @Override
  public Page<EvaluatorRes> findAllEvaluatorsUsePaging(Pageable pageable, String search, Long companyAdminId) {
    Page<EvaluatorRes> evaluators = evaluatorRepository.findAllEvaluatorsByCompanyAdminIdUsePaging(pageable, search, companyAdminId);
    return evaluators;
  }

  @Override
  public List<Evaluator> findAllEvaluators(Long companyAdminId) {
    //현재 기업관리자의 시퀀스넘버(id)를 알아야 한다.
    List<Evaluator> evaluators = evaluatorRepository.findAllEvaluatorsByCompanyAdminId(companyAdminId);
    return evaluators;
  }

  @Override
  public Evaluator findEvaluator(Long id) {
    Evaluator evaluator = evaluatorRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return evaluator;
  }

  @Override
  public Evaluator getEvaluatorByUserId(String userId) {
    Evaluator evaluator = evaluatorRepository.findEvaluatorByUserId(userId);
    return evaluator;
  }

  @Override
  public Evaluator getEvaluatorById(Long id) {
    Evaluator evaluator = evaluatorRepository.findEvaluatorById(id);
    return evaluator;
  }

  @Override
  public List<String> getDepartments(Long companyAdminId) {
    List<String> departments = evaluatorRepository.findAllDepartmentsByCompanyAdminId(companyAdminId);
    return departments;
  }

  @Override
  public Page<EvaluatorRes> findDepartmentEvaluators(Pageable pageable, List<String> departmentList, Long companyAdminId) {
    Page<EvaluatorRes> evaluators = evaluatorRepository.findDepartmentEvaluators(pageable, departmentList, companyAdminId);
    return evaluators;
  }

  @Override
  public List<InterviewRoomRes> getInterviewRoomsById(Long id) {
    List<InterviewRoomRes> interviewRooms = evaluatorRepository.findInterviewRoomsById(id);
    return interviewRooms;
  }

  /**
   * 평가자 이메일로 중복 회원 검증
   */
  private void validateDuplicateEvaluator(Evaluator evaluator) {

    Optional<Evaluator> findEvaluator = evaluatorRepository.findAllByEmail(evaluator.getEmail());
    if(findEvaluator.isPresent()){
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  /**
   * 비밀번호 자동 생성에 사용되는 랜덤 문자열 생성
   */
  private String getRandomString() {
    int leftLimit = 48;     //숫자 0
    int rightLimit = 122;   //영문자 z
    int targetStringLength = 10;    //10글자로 생성
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();

    return generatedString;
  }
}
