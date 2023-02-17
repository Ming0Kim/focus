package com.bb.focus.api.service;

import com.bb.focus.api.request.CompanyAdminRegisterPostReq;
import com.bb.focus.api.response.ApplicantLogRes;
import com.bb.focus.api.response.InterviewRoomRes;
import com.bb.focus.api.response.ProcessRes;
import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import com.bb.focus.db.entity.applicant.Status;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.repository.ApplicantPassLogRepository;
import com.bb.focus.db.repository.CompanyAdminRepository;
import com.bb.focus.db.repository.InterviewRoomRepository;
import com.bb.focus.db.repository.ProcessRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("companyAdminService")
public class CompanyAdminServiceImpl implements CompanyAdminService {

  CompanyAdminRepository companyAdminRepository;
  ProcessRepository processRepository;
  InterviewRoomRepository interviewRoomRepository;
  ApplicantPassLogRepository applicantPassLogRepository;

  @Autowired
  public CompanyAdminServiceImpl(CompanyAdminRepository companyAdminRepo
      , ProcessRepository processRepo
      , InterviewRoomRepository interviewRoomRepo
      , ApplicantPassLogRepository applicantPassLogRepo) {
    companyAdminRepository = companyAdminRepo;
    processRepository = processRepo;
    interviewRoomRepository = interviewRoomRepo;
    applicantPassLogRepository = applicantPassLogRepo;
  }

  @Override
  public CompanyAdmin createCompanyAdmin(CompanyAdminRegisterPostReq userRegisterInfo) {
    CompanyAdmin companyAdmin = new CompanyAdmin();

    String userId =
        userRegisterInfo.getCompanyName() + "CA" + userRegisterInfo.getStartDate().toLocalDate()
            .format(
                DateTimeFormatter.ofPattern("yyyyMMdd"));
    String pwd = getRandomString();
//    String pwd = EncryptionUtils.encryptSHA256(getRandomString());

    companyAdmin.setUserId(userId);
    companyAdmin.setPwd(pwd);
    companyAdmin.setCompanyName(userRegisterInfo.getCompanyName());
    companyAdmin.setStartDate(userRegisterInfo.getStartDate());
    companyAdmin.setEndDate(userRegisterInfo.getEndDate());
    companyAdmin.setTel(userRegisterInfo.getTel());
    companyAdmin.setEmail(userRegisterInfo.getEmail());
    companyAdmin.setName(userRegisterInfo.getName());
    companyAdmin.setIndustry(userRegisterInfo.getIndustry());
    companyAdmin.setSize(userRegisterInfo.getSize());
    companyAdmin.setLogoImage(userRegisterInfo.getLogoImage());
    companyAdmin.setSkinColor(userRegisterInfo.getSkinColor());
    companyAdmin.setUserRole(userRegisterInfo.getUserRole());
    return companyAdminRepository.save(companyAdmin);
  }

  @Override
  @Transactional
  public CompanyAdmin updateCompanyAdminUserId(CompanyAdmin companyAdminRevised) {
    CompanyAdmin companyAdmin = new CompanyAdmin();
    companyAdmin.setUserId(companyAdminRevised.getUserId());
    companyAdmin.setCompanyName(companyAdminRevised.getCompanyName());
    companyAdmin.setStartDate(companyAdminRevised.getStartDate());
    companyAdmin.setEndDate(companyAdminRevised.getEndDate());
    companyAdmin.setTel(companyAdminRevised.getTel());
    companyAdmin.setEmail(companyAdminRevised.getEmail());
    companyAdmin.setName(companyAdminRevised.getName());
    companyAdmin.setIndustry(companyAdminRevised.getIndustry());
    companyAdmin.setSize(companyAdminRevised.getSize());
    companyAdmin.setLogoImage(companyAdminRevised.getLogoImage());
    companyAdmin.setSkinColor(companyAdminRevised.getSkinColor());
    companyAdmin.setUserRole(companyAdminRevised.getUserRole());
    Long companyAdminId = companyAdminRepository.updateCompanyAdminByUserId(companyAdmin);
    return companyAdmin;
  }

  @Override
  public CompanyAdmin getCompanyAdminByUserId(String userId) {
    CompanyAdmin companyAdmin = companyAdminRepository.findCompanyAdminByUserId(userId);
    return companyAdmin;
  }

  @Override
  public CompanyAdmin getCompanyAdminById(Long id) {
    CompanyAdmin companyAdmin = companyAdminRepository.findCompanyAdminById(id);
    return companyAdmin;
  }

  @Override
  public List<CompanyAdmin> getAll() {
    List<CompanyAdmin> companyAdminList = companyAdminRepository.findAll();
    return companyAdminList;
  }

  @Override
  public Long updateCompanyAdminByUserInfo(CompanyAdminRegisterPostReq userUpdateInfo) {
    CompanyAdmin companyAdmin = new CompanyAdmin();
    companyAdmin.setCompanyName(userUpdateInfo.getCompanyName());
    companyAdmin.setStartDate(userUpdateInfo.getStartDate());
    companyAdmin.setEndDate(userUpdateInfo.getEndDate());
    companyAdmin.setTel(userUpdateInfo.getTel());
    companyAdmin.setEmail(userUpdateInfo.getEmail());
    companyAdmin.setName(userUpdateInfo.getName());
    companyAdmin.setIndustry(userUpdateInfo.getIndustry());
    companyAdmin.setSize(userUpdateInfo.getSize());
    companyAdmin.setLogoImage(userUpdateInfo.getLogoImage());
    companyAdmin.setSkinColor(userUpdateInfo.getSkinColor());
    companyAdmin.setUserRole(userUpdateInfo.getUserRole());
    Long companyAdminId = companyAdminRepository.updateCompanyAdminByUserId(companyAdmin);
    return companyAdminId;
  }

  @Override
  public int getAllEvaluationCount(Long companyAdminId) {
    CompanyAdmin companyAdmin = companyAdminRepository.findCompanyAdminById(companyAdminId);
    return companyAdmin.getEvaluatorList().size();
  }

  @Override
  public int getAllApplicantCount(Long companyAdminId) {
    CompanyAdmin companyAdmin = companyAdminRepository.findCompanyAdminById(companyAdminId);
    int applicantCount = 0;

    List<Process> processes = companyAdmin.getProcessList();
    for (Process process : processes) {
      applicantCount += process.getApplicantList().size();
    }

    return applicantCount;
  }

  @Override
  public List<ProcessRes> getAllProcess(Long companyAdminId) {
    List<ProcessRes> processResList = processRepository.findAllExpectedProcess(companyAdminId);
    return processResList;
  }

  @Override
  public List<InterviewRoomRes> getAllReservedInterview(Long processId) {
    Process process = processRepository.findById(processId).orElseThrow(IllegalAccessError::new);
    Long cur_step = (long) process.getCurrentStep();
    String processName = process.getName();
    List<InterviewRoom> interviewRoomList = interviewRoomRepository.findByProcessNameAndInterviewRound(
        processName, cur_step);
    List<InterviewRoomRes> interviewRoomResList = new ArrayList<>();

    for (InterviewRoom interviewRoom : interviewRoomList) {
      if (interviewRoom.getStartTime().isAfter(LocalDateTime.now())) {
        InterviewRoomRes interviewRoomRes = new InterviewRoomRes(interviewRoom);
        interviewRoomResList.add(interviewRoomRes);
      }
    }

    return interviewRoomResList;
  }


  @Override
  public List<ApplicantLogRes> getAllInterviewPerPassApplicant(Long processId) {
    Process process = processRepository.findById(processId).orElseThrow(IllegalAccessError::new);
    if (process == null) {
      return null;
    }
    Byte cur_step = process.getCurrentStep();
    String processName = process.getName();
    List<ApplicantPassLog> applicantPassLogsList = applicantPassLogRepository.findByProcessNameAndStep(
        processName, cur_step);
    List<ApplicantLogRes> applicantLogResList = new ArrayList<>();

    for (ApplicantPassLog applicantPassLog : applicantPassLogsList) {
      if (applicantPassLog.getStatus() == Status.P) {
        ApplicantLogRes applicantLogRes = new ApplicantLogRes(applicantPassLog);
        applicantLogResList.add(applicantLogRes);
      }
    }

    return applicantLogResList;
  }

  @Override
  public List<ProcessRes> getFinishStepPerProcessInfo(Long companyId) {
    List<Process> processList = processRepository.findAllByCompanyAdminId(companyId);
    List<ProcessRes> processResList = new ArrayList<>();
    for (Process process : processList) {
      if (process.getCurrentStep() > process.getInterviewCount()) {
        continue;
      }
      for (Interview interview : process.getInterviewList()) {
        if (process.getCurrentStep() == interview.getStep()) {
          if (LocalDateTime.now().isAfter(interview.getEndDate())) {
            ProcessRes processRes = new ProcessRes();
            processRes.GetProcess(process);
            processResList.add(processRes);
          }
        }
      }
    }

    return processResList;
  }

  @Override
  public List<CompanyAdmin> getAllContainsToday() {
    return companyAdminRepository.findAllContainsToday();
  }

  private String getRandomString() {
    int leftLimit = 48;     //숫자 0
    int rightLimit = 122;   //영문자 z
    int targetStringLength = 10;    //10글자로 생성
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
//        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();

    return generatedString;
  }

}
