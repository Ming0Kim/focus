package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.interview.InterviewRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "applicants_interview_rooms")
public class ApplicantInterviewRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_interview_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interview_room_id")
    private InterviewRoom interviewRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_id")
    private Applicant applicant;


}
