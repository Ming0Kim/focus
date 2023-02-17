package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollegeRepository extends JpaRepository<ApplicantCollege, Long> {

    ApplicantCollege findByName(String name);
    List<ApplicantCollege> findByNameLike(String name);

    List<ApplicantCollege> findByNameContainingIgnoreCase(String name);
}
