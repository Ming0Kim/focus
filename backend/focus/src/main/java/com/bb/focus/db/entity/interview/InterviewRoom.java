package com.bb.focus.db.entity.interview;

import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.helper.ApplicantInterviewRoom;
import com.bb.focus.db.entity.helper.EvaluatorInterviewRoom;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interview_rooms")
@Getter
@Setter
@NoArgsConstructor
public class InterviewRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

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
    private LocalDate date;

    @NotNull
    private int duration;

    @Column(length = 45)
    @NotNull
    private String processName;

    @NotNull
    private int interviewRound;

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ApplicantInterviewRoom.class,
        mappedBy = "interviewRoom", cascade = {CascadeType.REMOVE})
    private List<ApplicantInterviewRoom> ApplicantInterviewRoomList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.EvaluatorInterviewRoom.class,
        mappedBy = "interviewRoom", cascade = {CascadeType.REMOVE})
    private List<EvaluatorInterviewRoom> EvaluatorInteviewRoomList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.helper.ApplicantEvaluator.class,
        mappedBy = "interviewRoom", cascade = {CascadeType.REMOVE})
    private List<ApplicantEvaluator> applicantEvaluatorList = new ArrayList<>();


}
