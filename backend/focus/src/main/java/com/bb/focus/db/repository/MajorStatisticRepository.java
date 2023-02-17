package com.bb.focus.db.repository;

import com.bb.focus.db.entity.statistics.MajorPerApplicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorStatisticRepository extends JpaRepository<MajorPerApplicant,Long> {

}
