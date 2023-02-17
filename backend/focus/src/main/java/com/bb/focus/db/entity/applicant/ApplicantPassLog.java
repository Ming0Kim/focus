package com.bb.focus.db.entity.applicant;

import com.bb.focus.db.entity.helper.InteviewApplicantPassLog;
import com.bb.focus.db.entity.helper.ProcessApplicantPassLog;
import com.bb.focus.db.entity.process.Process;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="applicants_pass_log")
public class ApplicantPassLog {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="applicant_pass_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_id")
    private Applicant applicant;

    @NotNull
    @Column(length = 50)
    private String userId;

    @NotNull
    @Column(length = 20)
    private String code;

    @NotNull
    @Column(length = 45)
    private String name;

    @NotNull
    @Column(length = 45)
    private String email;

    @NotNull
    @Column(length = 64)
    private String processName;

    @NotNull
    @Column(length = 64)
    private String interviewName;

    @NotNull
    @ColumnDefault("1")
    private Byte step;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @ColumnDefault("0")
    private double score;

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.InteviewApplicantPassLog.class, mappedBy = "applicantPassLog")
    private List<InteviewApplicantPassLog> inteviewApplicantPassLogList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ProcessApplicantPassLog.class, mappedBy = "applicantPassLog")
    private List<ProcessApplicantPassLog> processApplicantPassLogList = new ArrayList<>();

    public boolean setApplicantData(Applicant applicant){
        try {
            this.code = applicant.getCode();
            this.name = applicant.getName();
            this.email = applicant.getEmail();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean setProcess(Process process){
        try{
            this.processName = process.getName();
            this.step = process.getCurrentStep();
        }catch ( Exception e){
            return false;
        }
        return true;
    }


}
