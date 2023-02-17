package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantCollegeRepository extends JpaRepository<ApplicantCollege, Long> {

    List<ApplicantCollege> findAll();

}
