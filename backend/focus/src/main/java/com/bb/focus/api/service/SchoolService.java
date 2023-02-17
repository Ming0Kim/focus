package com.bb.focus.api.service;

import com.bb.focus.api.response.SchoolDto;
import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import com.bb.focus.db.repository.CollegeRepository;

import java.util.List;

public interface SchoolService {

    // 모든 4년제 대학교 출력 함수
    public List<ApplicantUniv> GetAllUnivList();
    // 모든 2년제 대학교 출력 함수
    public List<ApplicantCollege> GetAllCollegeList();
    // 모든 대학원 출력 함수
    public List<ApplicantGraduate> GetAllGraduateSchoolList();

    // 이름에 맞는 4년제 대학 출력함수
    public ApplicantUniv GetUnivbyName(String name);
    // 이름에 맞는 2년제 대학 출력함수
    public ApplicantCollege GetCollegebyName(String name);
    // 이름에 맞는 대학원 출력함수
    public ApplicantGraduate GetGraduateSchoolbyName(String name);

    // 비슷한 이름을 가진 4년제 대학 출력함수
    public List<ApplicantUniv> GetUnivbyLikeName(String name);
    // 비슷한 이름을 가진 2년제 대학 출력함수
    public List<ApplicantCollege> GetCollegebyLikeName(String name);
    // 비슷한 이름을 가진 대학원 출력함수
    public List<ApplicantGraduate> GetGraduateSchoolbyLikeName(String name);

    // 4년제 대학교 insert 함수
    public boolean InsertUniv(SchoolDto schooldata);
    // 2년제 대학교 insert 함수
    public boolean InsertCollege(SchoolDto schooldata);
    // 대학원 insert 함수
    public boolean InsertGraduate(SchoolDto schooldata);
}
