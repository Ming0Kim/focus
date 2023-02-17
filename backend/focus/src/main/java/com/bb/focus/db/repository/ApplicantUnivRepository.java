package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantUnivRepository extends JpaRepository<ApplicantUniv, Long> {

    List<ApplicantUniv> findAll();

}
