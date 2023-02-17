package com.bb.focus.api.service;

import com.bb.focus.api.request.ApplicantInfoReq;
import com.bb.focus.api.response.ApplicantRes;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.repository.ApplicantRepository;
import com.bb.focus.db.repository.CollegeRepository;
import com.bb.focus.db.repository.CompanyAdminRepository;
import com.bb.focus.db.repository.GraduateSchoolRepository;
import com.bb.focus.db.repository.ProcessRepository;
import com.bb.focus.db.repository.UniversityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService{

  private final ApplicantRepository applicantRepository;

  private final CompanyAdminRepository companyAdminRepository;

  private final CollegeRepository collegeRepository;

  private final UniversityRepository universityRepository;

  private final GraduateSchoolRepository graduateSchoolRepository;
  private final MailService mailService;
  private final ProcessRepository processRepository;


  /**
   * 지원자 계정 생성
   */
  @Transactional
  public Long create(Long comapnyAdminId, ApplicantInfoReq applicantInfoReq, Long processId) {
    Applicant applicant = new Applicant();

    applicant.setName(applicantInfoReq.getName());
    applicant.setCode(applicantInfoReq.getCode());
    applicant.setGender(applicantInfoReq.getGender());
    applicant.setBirth(applicantInfoReq.getBirth());
    applicant.setEmail(applicantInfoReq.getEmail());
    applicant.setTel(applicantInfoReq.getTel());
    applicant.setDegree(applicantInfoReq.getDegree());
    applicant.setAwardCount(applicantInfoReq.getAwardCount());
    applicant.setActivityCount(applicantInfoReq.getActivityCount());
    applicant.setMajor(applicantInfoReq.getMajor());
    applicant.setTotalCredit(applicantInfoReq.getTotalCredit());
    applicant.setCredit(applicantInfoReq.getCredit());

    Process process = processRepository.findById(processId).orElseThrow(IllegalArgumentException::new);
    applicant.setProcess(process);

    if(applicantInfoReq.getCollegeId() != null){
      ApplicantCollege applicantCollege = collegeRepository.findById(applicantInfoReq.getCollegeId())
          .orElseThrow(IllegalArgumentException::new);
      applicant.setApplicationCollege(applicantCollege);
    }

    if(applicantInfoReq.getUnivId() != null){
      ApplicantUniv applicantUniv = universityRepository.findById(applicantInfoReq.getUnivId())
          .orElseThrow(IllegalArgumentException::new);
      applicant.setApplicantsUniv(applicantUniv);
    }

    if(applicantInfoReq.getGraduateId() != null){
      ApplicantGraduate applicantGraduate = graduateSchoolRepository.findById(applicantInfoReq.getGraduateId())
          .orElseThrow(IllegalArgumentException::new);
      applicant.setApplicantsGraduate(applicantGraduate);
    }

    CompanyAdmin companyAdmin = companyAdminRepository.findById(comapnyAdminId).orElseThrow(IllegalArgumentException::new);
    companyAdmin.addApplicant(applicant);

    applicantRepository.save(applicant);
    return applicant.getId();
  }

  /**
   * 지원자 아이디, 비밀번호 자동생성 및 할당
   * 생성 규칙] 아이디 : 기업이름 + A + 지원자 수험번호
   *          비밀번호 : 랜덤 생성 문자열
   */
  @Transactional
  public void autoAssignAccount(Long id) throws MessagingException {

    Applicant applicant = applicantRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    String newId = applicant.getCompanyAdmin().getCompanyName() + "A" + applicant.getCode();
    String newPwd = getRandomString();

    //메일
    Map<String, String> content = new HashMap<>();
    content.put("id", newId);
    content.put("pwd", newPwd);
    mailService.sendAccountMail(applicant.getEmail(), content);

    //암호화
//    String encodedPwd = EncryptionUtils.encryptSHA256(newPwd);
    String encodedPwd = newPwd;

    applicant.setUserId(newId);
    applicant.setPwd(encodedPwd);
  }

  /**
   * 지원자의 기본정보를 수정한다.
   * 수정 항목] 이름, 수험번호, 성별, 생년월일, 사진, 이메일, 전화번호, 자기소개서, 학위, 수상횟수, 대외활동 횟수, 대학정보(2, 4, 대학원)
   */
  @Transactional
  public Long updateApplicantInfo(Long id, ApplicantInfoReq applicantInfoReq) {
    Applicant applicant = applicantRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    applicant.setName(applicantInfoReq.getName());
    applicant.setCode(applicantInfoReq.getCode());
    applicant.setGender(applicantInfoReq.getGender());
    applicant.setBirth(applicantInfoReq.getBirth());
    applicant.setEmail(applicantInfoReq.getEmail());
    applicant.setTel(applicantInfoReq.getTel());
    applicant.setDegree(applicantInfoReq.getDegree());
    applicant.setAwardCount(applicantInfoReq.getAwardCount());
    applicant.setActivityCount(applicantInfoReq.getActivityCount());
    applicant.setMajor(applicantInfoReq.getMajor());
    applicant.setTotalCredit(applicantInfoReq.getTotalCredit());
    applicant.setCredit(applicantInfoReq.getCredit());

    //대학 정보 수정..
    if(applicantInfoReq.getCollegeId() != null){
      ApplicantCollege applicantCollege = collegeRepository.findById(applicantInfoReq.getCollegeId())
          .orElseThrow(IllegalArgumentException::new);
      applicant.setApplicationCollege(applicantCollege);
    }

    if(applicantInfoReq.getUnivId() != null){
      ApplicantUniv applicantUniv = universityRepository.findById(applicantInfoReq.getUnivId())
          .orElseThrow(IllegalArgumentException::new);
      applicant.setApplicantsUniv(applicantUniv);
    }

    if(applicantInfoReq.getGraduateId() != null){
      ApplicantGraduate applicantGraduate = graduateSchoolRepository.findById(applicantInfoReq.getGraduateId())
          .orElseThrow(IllegalArgumentException::new);
      applicant.setApplicantsGraduate(applicantGraduate);
    }

    return applicant.getId();
  }

  /**
   * 지원자 삭제
   */
  @Transactional
  public void removeApplicant(Long id) {
    applicantRepository.deleteById(id);
  }

  /**
   * 현재 회사에 소속되어있는 모든 지원자 목록을 조회한다.
   */
  public List<Applicant> findAllApplicants(Long companyAdminId) {
    List<Applicant> applicants = applicantRepository.findAllApplicantsByCompanyAdminId(companyAdminId);
    return applicants;
  }

  /**
   * 특정 지원자를 조회한다.
   */
  public Applicant findApplicant(Long id) {
    return applicantRepository.findById(id).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public Applicant getApplicantByUserId(String userId) {
    Applicant applicant = applicantRepository.findApplicantByUserId(userId);
    return applicant;
  }

  @Override
  public Applicant getApplicantById(Long id) {
    Applicant applicant = applicantRepository.findApplicantById(id);
    return applicant;
  }

  @Override
  public Page<ApplicantRes> findAllApplicantsUsePaging(Pageable pageable, String search, Long companyAdminId, Long processId) {
    Page<ApplicantRes> applicants = applicantRepository.findAllApplicantsWithPaging(pageable, search, companyAdminId, processId);
    return applicants;

  }

  /**
   * 비밀번호 자동생성에 사용되는 랜덤 문자열 생성기
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
