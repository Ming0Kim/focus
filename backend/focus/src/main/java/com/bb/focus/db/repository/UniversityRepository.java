package com.bb.focus.db.repository;

import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<ApplicantUniv, Long> {

    ApplicantUniv findByName(String name);
    List<ApplicantUniv> findByNameLike(String name);

    List<ApplicantUniv> findByNameContainingIgnoreCase(String name);


}
