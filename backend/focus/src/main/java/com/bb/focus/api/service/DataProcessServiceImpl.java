package com.bb.focus.api.service;


import com.bb.focus.api.response.SchoolDto;
import com.bb.focus.common.util.ExcelUtils;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.process.Process;
import com.bb.focus.db.entity.statistics.ApplicantStatistic;
import com.bb.focus.db.entity.statistics.MajorPerApplicant;
import com.bb.focus.db.repository.ApplicantRepository;
import com.bb.focus.db.repository.MajorStatisticRepository;
import com.bb.focus.db.repository.ProcessRepository;
import com.bb.focus.db.repository.StaticalRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessServiceImpl implements DataProcessService{

    MajorStatisticRepository majorStatisticRepo;
    StaticalRepository statisticRepo;
    ApplicantService applicantService;
    ProcessRepository processRepo;
    ExcelUtils exelUtils;
    ApplicantRepository applicantRepo;
    @Autowired
    public DataProcessServiceImpl(ExcelUtils eutil,ApplicantService aservice
                                  ,StaticalRepository staticalRepository
                                  ,MajorStatisticRepository majorStatisticRepository
                                  ,ProcessRepository processRepository
                                  ,ApplicantRepository applicantRepository)
    {
        applicantService = aservice;
        exelUtils = eutil;
        statisticRepo = staticalRepository;
        majorStatisticRepo = majorStatisticRepository;
        processRepo = processRepository;
        applicantRepo = applicantRepository;
    }

    public List<SchoolDto> ConvertMultiFileIntoList(MultipartFile file) throws IOException {
        if(exelUtils.FileNameFilter(file.getOriginalFilename()).equals("invalid")){
            return null;
        }
        List<SchoolDto> schoolDtoList = new ArrayList<>();
        String[] f;
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"EUC-KR"));

        while ((line = br.readLine()) != null) {
            f = line.split(",");

            SchoolDto schoolDto = new SchoolDto();
            schoolDto.setName(f[1]);
            schoolDto.setCampus(f[2]);
            schoolDto.setArea(f[3]);
            schoolDtoList.add(schoolDto);
        }
        br.close();
        return schoolDtoList;
    }
    //엑셀 타입에 따라 파일을 읽어오는 함수
    public List<String[]> ReadExcel(MultipartFile file, int columns) throws IOException,InvalidFormatException {

        String extension;

        List<String[]> Exceldata = new ArrayList<>();
        if((extension=exelUtils.FileNameFilter(file.getOriginalFilename())) == null){
            return Exceldata;
        }
        Workbook workbook = null;
        if(extension.equals("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());
        }else if(extension.equals("xls")){
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        Sheet worksheet = workbook.getSheetAt(0);

        for(int i=1; i<worksheet.getPhysicalNumberOfRows(); ++i){
            Row row = worksheet.getRow(i);
            String[] rowData = new String[columns];
            for(int j=0; j<columns; ++j){
                Cell cell  = row.getCell(j);

                if(rowData[j] == null || rowData[j].equals("")){
                    rowData[j] = "null";
                    continue;
                }

                if(cell.getCellType() == CellType.NUMERIC){
                    rowData[j] = String.valueOf(row.getCell(j).getNumericCellValue());
                } else{
                    rowData[j] = row.getCell(j).getStringCellValue();
                }
            }
            Exceldata.add(rowData);
        }

        return Exceldata;
    }

    // 다운로드할 엑셀파일을 만드는 함수
    public Workbook CreateWorkbook(String[] headers){
        return exelUtils.CreateWorkbook(headers);
    }



    //통계함수들
    // 통계 테이블에서 지원자 평균 나이를 가져오는 함수
    public long GetAvgAge(long processId){
        ApplicantStatistic applicantStatisticLog =statisticRepo.findByProcessId(processId);
        return applicantStatisticLog.getAvgApplicantAge();
    }

    // 통계 테이블에서 지원자 남성과 여성 비율을 가져오는 함수
    public Map<String,Integer> GetGenders(long processId){
        Map<String, Integer> Genders = null;
        ApplicantStatistic applicantStatisticLog =statisticRepo.findByProcessId(processId);

        Genders.put("female",applicantStatisticLog.getFemaleCount());
        Genders.put("male",applicantStatisticLog.getMaleCount());

        return Genders;
    }

    // 통계 테이블에서 각 지원자 전공 별 인원을 가져오는 함수 반환되는 데이터는 각 학과 Key 인원 Value로 반환됨.
    public Map<String,Integer> GetMajorPerApplicant(long processId){
        Map<String, Integer> StatisticMajorData = null;
        ApplicantStatistic applicantStatisticLog =statisticRepo.findByProcessId(processId);

        List<MajorPerApplicant> majorPerApplicantList=applicantStatisticLog.getMajorPerApplicantList();

        for(MajorPerApplicant majorPerApplicant:majorPerApplicantList){
            StatisticMajorData.put(majorPerApplicant.getMajorName(),majorPerApplicant.getApplicantCount());
        }

        return StatisticMajorData;
    }

    // 통계 테이블에서 지원자 평균 수상 횟수를 반환하는 함수
    public long GetAwardPerApplicant(long processId){
        ApplicantStatistic applicantStatisticLog =statisticRepo.findByProcessId(processId);
        return applicantStatisticLog.getAvgAwardCount();
    }
    // 통계 테이블에서 지원자 평균 대외활동 수를 반환하는 함수
    public long GetActivityPerApplicant(long processId){
        ApplicantStatistic applicantStatisticLog =statisticRepo.findByProcessId(processId);
        return applicantStatisticLog.getAvgApplicantAge();
    }


    // 통계 테이블에서 평균 학점 가져오는 함수
    public float GetAvgGradeApplicant(long processId){
        List<Applicant> applicantList = applicantRepo.findAllByProcessId(processId);
        float avgGrade = 0;
        int numberOfApplicant = applicantList.size();
        for(Applicant applicant:applicantList){
            if(applicant.getTotalCredit()  < 4.5){
                avgGrade += applicant.getCredit() + 0.3;
            }else{
                avgGrade += applicant.getCredit();
            }
        }
        return (avgGrade / numberOfApplicant);
    }

    // PDF의 형태로 통계 데이터를 반환해주는 함수. 취소
//    public MultipartFile ExtractStaticResultIntoPDF(){
//
//
//
//
//        return null;
//    }

    @Transactional
    public boolean CreateStatisticTable(long processId){
        // 전형 얻어오고 -> 전형 process 넣어주고 -> 각 값들 받아서 set해주고 -> save
        ApplicantStatistic applicantStatistic= new ApplicantStatistic();
        Process process = processRepo.findById(processId).orElseThrow(IllegalAccessError::new);
        applicantStatistic.setProcessId(process);
        statisticRepo.save(applicantStatistic);
        return true;
    }

    public boolean CreateMajorTable(Map<String,Integer> major, Long processId){
        ApplicantStatistic applicantStatistic=statisticRepo.findByProcessId(processId);
        Iterator<String> keys = major.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            int value = major.get(key);
            MajorPerApplicant majorPerApplicant = new MajorPerApplicant();
            majorPerApplicant.setMajorName(key);
            majorPerApplicant.setApplicantStatistic(applicantStatistic);
            majorPerApplicant.setApplicantCount(value);
            majorStatisticRepo.save(majorPerApplicant);
        }

        return true;
    }
    public Map<String,Integer> UpdateStatisticTable(long processId){
        ApplicantStatistic applicantStatistic = statisticRepo.findByProcessId(processId);
        List<Applicant> applicantList = applicantRepo.findAllByProcessId(processId);

        int age =0;
        int maleCount =0;
        int femaleCount = 0;
        int avgAwardCount =0;
        int avgAtivityCount =0;
        int college =0;
        int university =0;
        int graduateShool = 0;
        int size = applicantList.size();
        Map<String,Integer> major = new HashMap<>();
        for(Applicant applicant:applicantList){
            age += LocalDate.now().getYear() -applicant.getBirth().getYear();
            if(applicant.getGender().equals("f")){
                femaleCount++;
            }else{
                maleCount++;
            }

            if(applicant.getApplicantsGraduate() != null){
                graduateShool++;
            }else if(applicant.getApplicantsUniv() != null){
                university++;
            }else {
                college++;
            }
            avgAwardCount += applicant.getAwardCount();
            avgAtivityCount += applicant.getActivityCount();

            String applicantmajor = applicant.getMajor();
            if(major.containsKey(applicantmajor)){
                major.put(applicantmajor,major.get(applicantmajor)+1);
            }else{
                major.put(applicantmajor,0);
            }

        }

        applicantStatistic.setAvgApplicantAge(age/size); // 나이 평균
        applicantStatistic.setMaleCount(maleCount); // 남자 수
        applicantStatistic.setFemaleCount(femaleCount);// 여자 수
        applicantStatistic.setCollegeCount(college);
        applicantStatistic.setGraduateCount(graduateShool);
        applicantStatistic.setUniversityCount(university);
        applicantStatistic.setAvgAwardCount(avgAwardCount/size);  // 수상
        applicantStatistic.setAvgAcitivityCount(avgAtivityCount/size); // 대외활동
        statisticRepo.save(applicantStatistic);

        return major;
    }
    // PROCESSID를 통하여 전형에 맞는 통계 테이블을 호출 후 해당 통계 테이블에 학과 데이터를 추가하는 코드
    @Transactional
    public boolean setMajorPerCountData(long procecssId, String majorName, int count){
       ApplicantStatistic applicantStatistic =statisticRepo.findByProcessId(procecssId);
       MajorPerApplicant majorPerApplicant = new MajorPerApplicant();

       majorPerApplicant.setMajorName(majorName);
       majorPerApplicant.setApplicantCount(count);
       majorPerApplicant.setApplicantStatistic(applicantStatistic);

       majorStatisticRepo.save(majorPerApplicant);
       return true;
    }

}
