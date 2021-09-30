package com.example.webmvc_boot.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Log4j2
@Controller
public class FileController {

    @GetMapping("multipartFile")
    public String multipartFileUploadForm(){
        return "files/fileExample";
    }

    //---------------------- multipartFile 사용 -----------------
    @PostMapping("/multipartFile")
    public String multipartFileUpload(MultipartFile myFile, // file name 맞춰주기
                                     HttpServletRequest req,
                                     RedirectAttributes attributes) {

        /*
        업로드 경로 설정
        배포를 위해서 상대경로로 설정한다.
        이 때 webapp폴더와 하위 디렉토리를 생성하고, 파일 출력을 위해 디폴트서블렛핸들러를 선언해준다.
        */
        String rootPath = req.getSession().getServletContext().getRealPath("/");    // src/main/webapp
        String path = rootPath + "/upload/";                                           // rootPath + 디렉토리명

        // 파일명 설정
        String originalFilename = myFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExtension;

        // 파일 생성
        File file = new File(path + uploadFileName);

        // 리다이렉트 메세지
        String message = "";

        try {
            myFile.transferTo(file);
            // DB 저장
            message = originalFilename + " is uploaded";
        }
        catch (Exception e){
            message = e.getMessage();
        }
        finally {
            attributes.addFlashAttribute("message", message);
            return "redirect:/multipartFile";
        }
    }

    //----------------- MultopartHttpServletRequest 사용 : 받아내는 데이터가 많을 때 ----------------
    @PostMapping("/multipartFile2")
    public String MultopartHttpServletRequestUpload(MultipartHttpServletRequest mRequest,
                                                    HttpServletRequest req,
                                                    RedirectAttributes attributes) {

        // 업로드 경로 설정
        String rootPath = req.getServletContext().getRealPath("/");
        String path = rootPath + "/upload/";

        // 파일 분리하기
        List<MultipartFile> fileList = mRequest.getFiles("myFile2");
        String content = mRequest.getParameter("content");

        // 리다이렉트 메세지
        List<String> message2 = new ArrayList<>();

        // DB저장을 위한 파일 List
        List<HashMap<String, String>> upLoadFileList = new ArrayList<>();

        for(MultipartFile mf : fileList){

            // 파일명 설정
            String originalFilename = mf.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uploadFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExtension;

            // 파일명 저장
            HashMap<String, String> map = new HashMap<>();
            map.put("oFIle", originalFilename);
            map.put("uFile", uploadFileName);

            // 파일 생성
            File file = new File(path + uploadFileName);

            try {
                mf.transferTo(file);
                message2.add(originalFilename + " is uploaded");
                upLoadFileList.add(map);
            }
            catch (Exception e){
                message2.add("File Upload Fail!! " + e.getMessage());
            }
        }

        // DB저장
        attributes.addFlashAttribute("message2", message2);
        return "redirect:/multipartFile";

    }

    //----------------------파일 다운로드 (ServletContext 방식)--------------------
    @Autowired
    ServletContext servletContext;

    @GetMapping("filedownloader1/{filename}")
    public ResponseEntity fileDownloader1(@PathVariable String filename, HttpServletRequest req) throws FileNotFoundException {

        // 경로 설정 (상대경로)
        String uploadPath = req.getServletContext().getRealPath("/upload");

        // 파일 확장자
        String minType = servletContext.getMimeType(filename);
        MediaType mediaType = MediaType.parseMediaType(minType);

        File file = new File(uploadPath + File.separator + filename);
        InputStreamResource is = new InputStreamResource(new FileInputStream(file));

        // 다운로드창 셋팅
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "originalName.jpg")
                .contentType(mediaType)
                .contentLength(file.length())
                .body(is);

    }

    //----------------------파일 다운로드 (ResourceLoader 방식)----------------------
    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("filedownloader2/{filename}")
    public ResponseEntity fileDownloader2(@PathVariable String filename) throws IOException {
        
        // resourcrLoader를 통한 경로 로드 및 파일 생성
        Resource resource = resourceLoader.getResource("/upload/" + filename);
        File file = resource.getFile();
        
        // 경로 추출 템플릿 (tika core 의존성 필요)
        Tika tika = new Tika();
        String mediaType = tika.detect(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=" + "originalName.jpg" )
                .header(HttpHeaders.CONTENT_TYPE, mediaType)
                .header(HttpHeaders.CONTENT_LENGTH, file.length() + "")
                .body(resource);
    }

}
