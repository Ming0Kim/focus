package com.bb.focus.db.repository;

import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.bb.focus.db.entity.statistics.ApplicantStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaticalRepository extends JpaRepository<ApplicantStatistic, Long> {
        ApplicantStatistic findByProcessId(long processId);
}
