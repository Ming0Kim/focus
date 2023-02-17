package com.bb.focus.common.util;

import java.io.*;
import java.nio.file.Files;
import java.util.Locale;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ImageUtil {

  private final String filePath = "/etc";
  private final String companyLogoPath = filePath + "/logo";
  private final String introducePath = filePath + "/introduce";
  private final String applicantFacePath = filePath + "/applicant";
  private final String evaluatorFacePath = filePath + "/evaluator";
  public boolean ExtensionCheck(MultipartFile file){
    String extension = FilenameUtils
                      .getExtension(file.getOriginalFilename())
                      .toLowerCase(Locale.ENGLISH);

    if(extension.equals("jpg")  || extension.equals("png") || extension.equals("jpeg")){
      return true;
    }

    return false;
  }
  public String getFilePathByKeyword(String keyword){
    if(keyword.equals("applicant")){
      return applicantFacePath;
    }else if(keyword.equals("company")){
      return companyLogoPath;
    }else if(keyword.equals("evaluator")){
     return evaluatorFacePath;
    }else {
      return introducePath;
    }
  }
//  public int getImageCount(String keyword){
//    keyword = getFilePathByKeyword(keyword);
//    File directory = new File(keyword);
//    File[] files = directory.listFiles();
//    if(files == null){
//      return -1;
//    }
//    int fileCount =0;
//
//    for(int i=0; i<files.length; ++i){
//      File file = files[i];
//
//      if(file.isFile()) {
//        fileCount++;
//      }
//    }
//    return fileCount;
//  }

  public byte[] ReadImage(String keyword, String fileName) throws FileNotFoundException, IOException{

    byte[] imageByteArray;
    String folderPath=getFilePathByKeyword(keyword);
    String path = folderPath + '/'+ fileName;

    if(!isFileExist(path)){
      return null;
    }

    InputStream imageInput = new FileInputStream(path);
    imageByteArray=IOUtils.toByteArray(imageInput);
    imageInput.close();

    return imageByteArray;
  }
  public String Upload(MultipartFile file,String baseFileName,Long uniqueValue){
    String saveFilePath = getFilePathByKeyword(baseFileName);
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    String saveName = baseFileName + uniqueValue+ "."+extension;
    saveFilePath += "/"+saveName;

    try {
      File createdFile = new File(saveFilePath);
      file.transferTo(createdFile);
    }catch(IOException e){
        return null;
    }
    return saveName;
  }

  public boolean Delete(String fileName,String folderPath){
    String path = folderPath + '/' + fileName;
    if(!isFileExist(path)){
      return false;
    }
    File file = new File(path);
    file.delete();

    return true;
  }

  public boolean isFileExist(String filePath){
    File file = new File(filePath);

    if(!file.exists()){
      return false;
    }
    if(!file.isFile()){
      return false;
    }

    return true;
  }
  public String GetContentType(String keyword,String fileName) throws IOException{
    String realPath = getFilePathByKeyword(keyword) +"/" + fileName;
    File file =new File(realPath);
    return Files.probeContentType(file.toPath());
  }
}
