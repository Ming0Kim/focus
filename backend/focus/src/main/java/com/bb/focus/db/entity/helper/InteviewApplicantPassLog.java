package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import com.bb.focus.db.entity.interview.Interview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="interview_applicants_pass_log")
public class InteviewApplicantPassLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interview_applicants_pass_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interview_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicants_pass_log_id")
    private ApplicantPassLog applicantPassLog;



}
