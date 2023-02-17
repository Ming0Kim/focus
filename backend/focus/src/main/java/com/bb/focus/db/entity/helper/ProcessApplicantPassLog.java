package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import com.bb.focus.db.entity.process.Process;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="process_applicants_pass_log")
public class ProcessApplicantPassLog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="process_applicants_pass_log")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="process_id")
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicants_pass_log_id")
    private ApplicantPassLog applicantPassLog;

}
