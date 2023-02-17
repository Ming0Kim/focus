package com.bb.focus.db.entity.helper;

import com.bb.focus.db.entity.evaluator.Evaluator;
import com.bb.focus.db.entity.process.Process;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "process_evaluators")
@Getter
@Setter
@NoArgsConstructor
public class ProcessEvaluator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "process_evaluators_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="process_id")
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    private Evaluator evaluator;
}
