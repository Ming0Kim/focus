package com.bb.focus.db.repository;

import com.bb.focus.db.entity.evaluation.EvaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationSheetItemRepository extends JpaRepository<EvaluationItem, Long> {
    Optional<EvaluationItem> findById(long ItemId);

}
