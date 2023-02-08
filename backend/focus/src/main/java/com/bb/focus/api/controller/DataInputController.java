package com.bb.focus.api.controller;

import com.bb.focus.api.response.SchoolDto;
import com.bb.focus.api.service.DataProcessService;
import com.bb.focus.api.service.SchoolService;
import com.bb.focus.common.util.ImageUtil;
import com.bb.focus.db.entity.applicant.Applicant;
import com.bb.focus.db.entity.applicant.school.ApplicantCollege;
import com.bb.focus.db.entity.applicant.school.ApplicantGraduate;
import com.bb.focus.db.entity.applicant.school.ApplicantUniv;
import com.bb.focus.db.repository.ApplicantRepository;
import com.bb.focus.db.repository.CollegeRepository;
import com.bb.focus.db.repository.GraduateSchoolRepository;
import com.bb.focus.db.repository.UniversityRepository;
import io.swagger.annotations.Api;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Api(value = "데이터 입력 API", tags = {"DataInput"})
@RestController
@CrossOrigin("*")
@RequestMapping("/api/Data")
public class DataInputController {
    private final DataProcessService DataService;
    private final SchoolService schoolSerivce;
    private final ImageUtil imageUtil;
    private final ApplicantRepository applicantRepository;
    private final UniversityRepository universityRepository;
    private final CollegeRepository collegeRepository;
    private final GraduateSchoolRepository graduateSchoolRepository;

    //ApplicantService
    //EvaluatorService
    @Autowired
    public DataInputController(DataProcessService Dservice, SchoolService scService, ImageUtil iUtil,
        ApplicantRepository applicantRepository, UniversityRepository universityRepository,
        CollegeRepository collegeRepository, GraduateSchoolRepository graduateSchoolRepository){
        DataService = Dservice;
        schoolSerivce = scService;
        imageUtil = iUtil;
        this.applicantRepository = applicantRepository;
        this.universityRepository = universityRepository;
        this.collegeRepository = collegeRepository;
        this.graduateSchoolRepository = graduateSchoolRepository;
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> UploadImage(@RequestPart MultipartFile file){
        if(!imageUtil.ExtensionCheck(file)){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if(!imageUtil.Upload(file,"self")){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 평가자 엑셀 다운로드 하는 함수 Headers 값의 따라 목록이 늘어남.
    @GetMapping("/download/evaluator")
    public ResponseEntity<?> DownloadEvaluatorExcel(HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment;filename=evaluator_input_here.xlsx");
        response.setContentType("application/octet-stream");
        String[] headers ={"이름","사번","부서","직급","전화번호","이메일"};
        Workbook workbook = DataService.CreateWorkbook(headers);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            IOUtils.copy(new ByteArrayInputStream(outputStream.toByteArray()),response.getOutputStream());
        }catch(IOException e){
            return new ResponseEntity<String>("Fail to download",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    // 지원자 엑셀 다운로드 하는 함수 Headers 값의 따라 목록이 늘어남.
    @GetMapping("/download/applicant")
    public ResponseEntity<?> DownloadApplicantExcel(HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment;filename=Applicant_input_here.xlsx");
        response.setContentType("application/octet-stream");
        String[] headers ={"수험번호","이름","성별","생년월일","이메일","전화번호","학위"};
        Workbook workbook = DataService.CreateWorkbook(headers);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            IOUtils.copy(new ByteArrayInputStream(outputStream.toByteArray()),response.getOutputStream());
        }catch(IOException e){
            return new ResponseEntity<String>("Fail to download",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 지원자 데이터를 엑셀로 받아오는 함수. csv 연동 아직 안됨. xls과 xlxs 두 가지만 가능
    @PostMapping(value = "/input/{process-id}/applicant",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddApplicantIntoExcel(
        @PathVariable(name="process-id") Long processId, @RequestPart MultipartFile file) throws IOException
    {
        List<String[]> data = null;
        try {
            data = DataService.ReadExcel(file,15);
        }catch (IOException e){
            return new ResponseEntity<String>("File was Broken",HttpStatus.BAD_REQUEST);
        }catch (InvalidFormatException e){
            return new ResponseEntity<String>("Invalid File Format",HttpStatus.BAD_REQUEST);
        }

        Applicant applicant = new Applicant();
        for(String[] da : data){
            System.out.println(Arrays.toString(da));
            applicant.setCode(da[0]);
            applicant.setName(da[1]);
            applicant.setTel(da[2]);
            applicant.setBirth(LocalDate.parse(da[3], DateTimeFormatter.ISO_DATE));
            applicant.setEmail(da[4]);
            applicant.setGender(da[5]);
            applicant.setDegree(da[6]);

            if(da[7].equals("null")){
                ApplicantUniv applicantUniv = universityRepository.findById(Long.parseLong(da[7]))
                    .orElseThrow(IllegalArgumentException::new);
                applicant.setApplicantsUniv(applicantUniv);
            }

            if(da[8].equals("null")){
                ApplicantCollege applicantCollege = collegeRepository.findById(Long.parseLong(da[8]))
                    .orElseThrow(IllegalArgumentException::new);
                applicant.setApplicationCollege(applicantCollege);
            }

            if(da[9].equals("null")){
                ApplicantGraduate applicantGraduate = graduateSchoolRepository.findById(Long.parseLong(da[9]))
                    .orElseThrow(IllegalArgumentException::new);
                applicant.setApplicantsGraduate(applicantGraduate);
            }

            applicant.setMajor(da[10]);
            applicant.setTotalCredit(Float.parseFloat(da[11]));
            applicant.setCredit(Float.parseFloat(da[12]));
            applicant.setActivityCount(Byte.parseByte(da[13]));
            applicant.setAwardCount(Byte.parseByte(da[14]));
        }

        applicantRepository.save(applicant);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 평가자 데이터를 엑셀로 받아오는 함수. csv 연동 아직 안됨. xls과 xlxs 두 가지만 가능
    @PostMapping(value = "/input/{company-id}/evaluator",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddEvaluatorIntoExcel(@RequestPart MultipartFile file) throws IOException
    {   List<String[]> data = null;
        try {
            data =DataService.ReadExcel(file,6);
        }catch (IOException e){
            return new ResponseEntity<String>("File was Broken",HttpStatus.BAD_REQUEST);
        }catch (InvalidFormatException e){
            return new ResponseEntity<String>("Invalid File Format",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 4년제 대학교 데이터를 엑셀로 받아오는 함수. csv 파일에 UTF-8 고정
    @PostMapping("/input/univ")
    public ResponseEntity<?> UniversityIntoExcel(@RequestPart MultipartFile file) {
        List<SchoolDto> univList=null;
        if(file.isEmpty()){
            return new ResponseEntity<String>("fail to load data",HttpStatus.BAD_REQUEST);
        }
        try {
            univList = DataService.ConvertMultiFileIntoList(file);
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }

        for(SchoolDto university: univList){
            schoolSerivce.InsertUniv(university);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 2년제 대학교 데이터를 엑셀로 받아오는 함수. csv 파일에 UTF-8 고정
    @PostMapping("/input/college")
    public ResponseEntity<?> CollegeIntoExcel(@RequestPart MultipartFile file) {
        List<SchoolDto> collegeList = null;
        try {
            collegeList = DataService.ConvertMultiFileIntoList(file);
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }
        for(SchoolDto colloge: collegeList){
            schoolSerivce.InsertCollege(colloge);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 대학원 데이터를 엑셀로 받아오는 함수. csv 파일에 UTF-8 고정
    @PostMapping("/input/graduateschool")
    public ResponseEntity<?> GraduateSchoolIntoExcel(@RequestPart MultipartFile file){
        List<SchoolDto> GraduateList = null;
        if(file == null) {
            return new ResponseEntity<String>("file not found",HttpStatus.BAD_REQUEST);
        }

        try {
            GraduateList = DataService.ConvertMultiFileIntoList(file);

        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }

        for(SchoolDto graduateSchool:GraduateList) {
            schoolSerivce.InsertGraduate(graduateSchool);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }



}