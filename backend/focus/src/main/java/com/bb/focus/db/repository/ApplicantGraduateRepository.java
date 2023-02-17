package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantGraduateRepository extends JpaRepository<ApplicantGraduate, Long> {

    List<ApplicantGraduate> findAll();

}
