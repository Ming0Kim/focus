package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.evaluator.Evaluator;
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
@Table(name = "interview_evaluators")
public class InterviewEvaluator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_evaluators_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interview_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evaluator_id")
    private Evaluator evaluator;

}
