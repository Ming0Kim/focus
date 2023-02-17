package com.bb.focus.api.service;

import com.bb.focus.api.response.SchoolDto;
import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import com.bb.focus.db.repository.CollegeRepository;
import com.bb.focus.db.repository.GraduateSchoolRepository;
import com.bb.focus.db.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;

@Service
public class SchoolServiceImpl extends ApplicantUniv implements SchoolService{

    UniversityRepository univRepo;
    GraduateSchoolRepository graduateRepo;
    CollegeRepository collegeRepo;
    @Autowired
    public SchoolServiceImpl(UniversityRepository univR,CollegeRepository collgeR, GraduateSchoolRepository graduateR){
        univRepo = univR;
        graduateRepo = graduateR;
        collegeRepo = collgeR;
    }

    public List<ApplicantUniv> GetAllUnivList(){
        return univRepo.findAll();
    }
    public List<ApplicantCollege> GetAllCollegeList(){
        return collegeRepo.findAll();
    }
    public List<ApplicantGraduate> GetAllGraduateSchoolList(){
        return graduateRepo.findAll();
    }

    public ApplicantUniv GetUnivbyName(String name){
        return univRepo.findByName(name);
    }
    public ApplicantCollege GetCollegebyName(String name){
        return collegeRepo.findByName(name);
    }
    public ApplicantGraduate GetGraduateSchoolbyName(String name){
        return graduateRepo.findByName(name);
    }

    public List<ApplicantUniv> GetUnivbyLikeName(String name){
        return univRepo.findByNameContainingIgnoreCase(name);
    }
    public List<ApplicantCollege> GetCollegebyLikeName(String name){
        return collegeRepo.findByNameContainingIgnoreCase(name);
    }
    public List<ApplicantGraduate> GetGraduateSchoolbyLikeName(String name){
        return graduateRepo.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public boolean InsertUniv(SchoolDto schooldata){
        ApplicantUniv univData = new ApplicantUniv();
        univData.setName(schooldata.getName());
        univData.setArea(schooldata.getArea());
        univData.setCampus(schooldata.getCampus());

        if(univRepo.save(univData) != null){
            return true;
        }
        return false;
    }
    @Transactional
    public boolean InsertCollege(SchoolDto schooldata){
        ApplicantCollege collegeData = new ApplicantCollege();
        collegeData.setName(schooldata.getName());
        collegeData.setArea(schooldata.getArea());
        collegeData.setCampus(schooldata.getCampus());

        if(collegeRepo.save(collegeData) != null){
            return true;
        }
        return false;
    }
    @Transactional
    public boolean InsertGraduate(SchoolDto schooldata){
        ApplicantGraduate graduateData = new ApplicantGraduate();
        graduateData.setName(schooldata.getName());
        graduateData.setArea(schooldata.getArea());
        graduateData.setCampus(schooldata.getCampus());

        if(graduateRepo.save(graduateData) != null){
            return true;
        }
        return false;
    }

}
