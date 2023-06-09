package com.bb.focus.db.entity.interview;

import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.helper.ApplicantInterviewRoom;
import com.bb.focus.db.entity.helper.EvaluatorInterviewRoom;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interview_rooms")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_admin_id")
    private CompanyAdmin companyAdmin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_chat_id")
    private RoomChat roomChat;

    @NotNull
    @Column(length = 45)
    private String name;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private int duration;

    @NotNull
    private Byte curEvaluatorCount;

    @NotNull
    private Byte curApplicantCount;

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ApplicantInterviewRoom.class, mappedBy = "interviewRoom")
    private List<ApplicantInterviewRoom> applicantInterviewRoomList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.EvaluatorInterviewRoom.class, mappedBy = "interviewRoom")
    private List<EvaluatorInterviewRoom> evaluatorInterviewRoomList = new ArrayList<>();
}
