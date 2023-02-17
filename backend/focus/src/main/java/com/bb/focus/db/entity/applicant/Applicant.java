package com.bb.focus.db.entity.applicant;

import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import com.bb.focus.db.entity.company.Chat;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.evaluation.EvaluationSheet;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.helper.ApplicantInterviewRoom;
import com.bb.focus.db.entity.process.Process;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Table(name = "applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_college_id")
    private ApplicantCollege applicationCollege;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_univ_id")
    private ApplicantUniv applicantsUniv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_graduate_id")
    private ApplicantGraduate applicantsGraduate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_admin_id")
    private CompanyAdmin companyAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    @Column(length = 50)
    private String userId;

    @Column(length = 64)
    private String pwd;

    @NotNull
    @Column(length = 45)
    private String code;

    @NotNull
    @Column(length = 45)
    private String name;

    @NotNull
    @Column(length = 1)
    private String gender;

    @NotNull
    private LocalDate birth;

    @NotNull
    @Column(length = 2090)
    private String image;

    @NotNull
    @Column(length = 45)
    private String email;

    @NotNull
    @Column(length = 13)
    private String tel;

    @NotNull
    @Column(length = 2090)
    private String resume;

    @NotNull
    @ColumnDefault("0")
    private Byte pass;

    @Column(length = 1)
    private String degree;

    private LocalDateTime expireDate;

    @NotNull
    @ColumnDefault("4")
    private Byte userRole;

    @NotNull
    @ColumnDefault("0")
    private Byte awardCount;

    @NotNull
    @ColumnDefault("0")
    private Byte activityCount;

    @NotNull
    @Column(length = 255)
    private String major;

    @NotNull
    private float totalCredit;

    @NotNull
    private float credit;

    @OneToMany(targetEntity = com.bb.focus.db.entity.applicant.ApplicantPassLog.class, mappedBy = "applicant")
    private List<ApplicantPassLog> applicantPassLogList = new ArrayList<>();

    @OneToOne(targetEntity = com.bb.focus.db.entity.company.Chat.class, mappedBy = "applicant", fetch = FetchType.LAZY)
    private Chat chat;

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ApplicantInterviewRoom.class,
        mappedBy = "applicant", cascade = {CascadeType.REMOVE})
    private List<ApplicantInterviewRoom> applicantInterviewRoomList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ApplicantEvaluator.class,
            mappedBy = "applicant", cascade = {CascadeType.REMOVE})
    private  List<ApplicantEvaluator> applicantEvaluatorList = new ArrayList<>();
    //연관관계 메서드
    public void setProcess(Process process){
        if(this.process != null){
            this.process.getApplicantList().remove(this);
        }
        this.process = process;
        process.getApplicantList().add(this);
    }

    public void addApplicantPasslog(ApplicantPassLog applicantPassLog){
        this.applicantPassLogList.add(applicantPassLog);
        if(applicantPassLog.getApplicant() != this){
            applicantPassLog.setApplicant(this);
        }
    }

}
