package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.ApplicantPassLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantPassLogRepository extends JpaRepository<ApplicantPassLog,Long> {

    List<ApplicantPassLog> findByProcessNameAndStep(String processName,Byte Step);


}
