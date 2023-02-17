package com.bb.focus.api.service;

import com.bb.focus.api.request.ProcessReq;
import com.bb.focus.api.response.InterviewRes;
import com.bb.focus.api.response.ProcessDetailRes;
import com.bb.focus.api.response.ProcessRes;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.repository.CompanyAdminRepository;
import com.bb.focus.db.repository.ProcessRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProcessServiceImpl implements ProcessService {

  private final ProcessRepository processRepository;
  private final CompanyAdminRepository companyAdminRepository;

  @Transactional
  public Long createProcess(ProcessReq processReq, Long companyAdminId) {
    CompanyAdmin companyAdmin = companyAdminRepository.findById(companyAdminId).orElseThrow(IllegalArgumentException::new);
    Process process = new Process();

    process.setCompanyAdmin(companyAdmin);
    process.setInterviewCount(processReq.getInterviewCount());
    process.setName(processReq.getName());
    if(processReq.getStartDate() != null){
      process.setStartDate(processReq.getStartDate());
    }
    if(processReq.getEndDate() != null){
      process.setEndDate(processReq.getEndDate());
    }
    processRepository.save(process);
    return process.getId();
  }

  @Transactional
  public Long updateProcess(Long id, ProcessReq processReq) {

    Process process = processRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    process.setInterviewCount(processReq.getInterviewCount());
    process.setName(processReq.getName());
    if(processReq.getStartDate() != null){
      process.setStartDate(processReq.getStartDate());
    }
    if(processReq.getEndDate() != null){
      process.setEndDate(processReq.getEndDate());
    }

    return process.getId();
  }

  @Transactional
  public void removeProcess(Long id) {
    processRepository.deleteById(id);
  }

  public List<ProcessRes> getAllExpectedProcess(Long companyAdminId) {
    List<ProcessRes> expectedProcessList = processRepository.findAllExpectedProcess(companyAdminId);
    return expectedProcessList;
  }

  public ProcessDetailRes getProcessDetail(Long processId) {
    ProcessDetailRes processDetail = processRepository.findProcessDetail(processId);
    List<Interview> list = processRepository.findById(processId).get().getInterviewList();
    List<InterviewRes> interviewList = list.stream()
        .map(i -> new InterviewRes(i))
        .collect(Collectors.toList());

    for(InterviewRes interview : interviewList){
      processDetail.getInterviewList().add(interview);
    }

    return processDetail;
  }

  public int getProcessGoingOnNumber(){
    return processRepository.findProcessGoingOn();
  }

  public List<ProcessRes> getAllPassedProcess(Long companyAdminId){
    List<ProcessRes> processResList = new ArrayList<>();
    List<Process> processes = processRepository.findAllByCompanyAdminIdAndCurrentStep(companyAdminId,(byte)2);
    for(Process process:processes){
      ProcessRes processRes = new ProcessRes();
      processRes.GetProcess(process);
      processResList.add(processRes);
    }

    return processResList;
  }
}
