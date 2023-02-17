package com.bb.focus.db.entity.evaluation;

import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor()
@Table(name = "evaluation_items")
public class EvaluationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evaluation_sheet_id")
    private EvaluationSheet evaluationSheet;

    @OneToMany(targetEntity = com.bb.focus.db.entity.evaluation.EvaluationResult.class,
        mappedBy = "evaluationItem")
    private List<EvaluationResult> evaluationResultList = new ArrayList<>();

    @NotNull
    @Column(length = 500)
    private String scaleContent;

    @NotNull
    private Byte scoreRange;

}