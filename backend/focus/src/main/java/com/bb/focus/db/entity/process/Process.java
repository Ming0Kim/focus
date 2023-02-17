package com.bb.focus.db.entity.process;

import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.helper.ProcessApplicantPassLog;
import com.bb.focus.db.entity.helper.ProcessEvaluator;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.statistics.ApplicantStatistic;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Process {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="process_id")
  private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_admin_id")
    private CompanyAdmin companyAdmin;

    @NotNull
    private Byte interviewCount;

    @NotNull
    @Column(length = 64)
    private String name;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @NotNull
    @ColumnDefault("1")
    private Byte currentStep;

    @OneToMany(targetEntity = com.bb.focus.db.entity.interview.Interview.class, mappedBy = "process")
    private List<Interview> interviewList = new ArrayList<>();


    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ProcessApplicantPassLog.class, mappedBy = "process")
    private List<ProcessApplicantPassLog> processApplicantPassLogList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ProcessEvaluator.class, mappedBy = "process")
    private  List<ProcessEvaluator> processEvaluatorList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.applicant.Applicant.class, mappedBy = "process")
    private List<Applicant> applicantList = new ArrayList<>();

    @OneToOne(targetEntity = com.bb.focus.db.entity.statistics.ApplicantStatistic.class,
        mappedBy = "processId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private ApplicantStatistic applicantStatistic;

    public void AddProcessPassLog(ApplicantPassLog applicantPassLog){
      ProcessApplicantPassLog processApplicantPassLog=new ProcessApplicantPassLog();
      processApplicantPassLog.setApplicantPassLog(applicantPassLog);
      processApplicantPassLog.setProcess(this);
      processApplicantPassLogList.add(processApplicantPassLog);
    }

    //연관관계 메서드
    public void setCompanyAdmin(CompanyAdmin companyAdmin){
      if(this.companyAdmin != null){
        this.companyAdmin.getProcessList().remove(this);
      }
      this.companyAdmin = companyAdmin;
      companyAdmin.getProcessList().add(this);
    }

}
