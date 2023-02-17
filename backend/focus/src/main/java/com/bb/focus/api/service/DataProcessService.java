package com.bb.focus.api.service;

import com.bb.focus.api.response.SchoolDto;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public interface DataProcessService {
  // file upload 시에 올바른 파일이 맞는 지 확인하는 함수

  // Multifile 타입으로 받은 CSV파일을 school로 변환 시켜 리스트로 반환하는 함수
  List<SchoolDto> ConvertMultiFileIntoList(MultipartFile file) throws IOException;
  //엑셀 타입에 따라 파일을 읽어오는 함수
  List<String[]> ReadExcel(MultipartFile file, int columns) throws IOException,InvalidFormatException;
  // 다운로드할 엑셀파일을 만드는 함수
  Workbook CreateWorkbook(String[] headers);

  long GetAvgAge(long processId);

  Map<String,Integer> GetGenders(long processId);

    Map<String,Integer> GetMajorPerApplicant(long processId);
  long GetAwardPerApplicant(long processId);
  long GetActivityPerApplicant(long processId);
  float GetAvgGradeApplicant(long processId);

  boolean CreateStatisticTable(long processId);

  Map<String,Integer> UpdateStatisticTable(long processId);

//  MultipartFile ExtractStaticResultIntoPDF();
  boolean CreateMajorTable(Map<String,Integer> major, Long processId);
}
