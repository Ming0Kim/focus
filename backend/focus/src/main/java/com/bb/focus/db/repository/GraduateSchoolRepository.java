package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraduateSchoolRepository extends JpaRepository<ApplicantGraduate, Long> {

    ApplicantGraduate findByName(String name);
    List<ApplicantGraduate> findByNameLike(String name);

    List<ApplicantGraduate> findByNameContainingIgnoreCase(String name);
}
