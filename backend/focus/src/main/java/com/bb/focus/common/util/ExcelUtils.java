package com.bb.focus.common.util;

import com.bb.focus.api.response.StatisticRes;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;


@Component
public class ExcelUtils {
    
    //엑셀 확장자를 검사하는 함수
    public String FileNameFilter(String fileName){
        String extension = FilenameUtils.getExtension(fileName);
        if(extension.equals("xls") || extension.equals("xlsx")  || extension.equals("csv")){
            return extension;
        }
        return "invalid";
    }
    
    //엑셀 워크시트를 만드는 함수
    public Workbook CreateWorkbook(String[] headers){
        Workbook workbook = new XSSFWorkbook();
        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFont(font);
        try{

            Sheet sheet = workbook.createSheet("sheet 1");
            Row row = sheet.createRow(0);
            Cell cell = null;
            for(int i=0; i<headers.length; ++i){
                cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(cellStyle);
            }
        }catch(Exception e){
            return null;
        }
        return workbook;
    }
    private void WritePdfTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setPhrase( new Phrase(""));
        table.addCell(cell);
    }

    public void ExtractPDF(HttpServletResponse response, List<StatisticRes> statisticData) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();
        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(25);
        font.setColor(java.awt.Color.BLACK);
        Paragraph para = new Paragraph("Focus - Statistic",font);
        para.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(para);
        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(10);
        String date = LocalDateTime.now().toString();
        para = new Paragraph(date,font);
        document.add(para);

        document.addTitle("Company Applicant Statistics Analysis Report");
        document.addCreator("BlackBunny");
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[] {1.5f,3.5f,3.0f});



        for(StatisticRes Data:statisticData){

        }

    }


}
