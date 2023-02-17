package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.evaluator.Evaluator;
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
@Table(name = "evaluators_interview_rooms")
public class EvaluatorInterviewRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluator_interview_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evaluator_id")
    private Evaluator evaluator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interview_room_id")
    private InterviewRoom interviewRoom;

}
