package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.InitBinderDto;
import com.example.webmvc_boot.exception.CustomException;
import com.example.webmvc_boot.formatter.customFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static com.example.webmvc_boot.exception.ErrorCode.INVALID_AUTH_TOKEN;

/*
RestControllerAdvice (basePackages="myController ")
전역 컨트롤러 지정.
일반 컨트롤러에서 InitBinder, ExceptionHandler, ModelAttribute를 설정하면 해당 컨트콜러에서만 적용된다
*/
@RestControllerAdvice (assignableTypes={FileController.class, JsonViewController.class})
public class GlobalController {

    // ------------- 추가 설정을 하고 싶을 때 사용 ------------
    @InitBinder("/test")
    public void binder(WebDataBinder webDataBinder){
        // 입력 받고싶지 않은 field값
        webDataBinder.setDisallowedFields("myData1");
        // 커스텀 formatter (webConfig에 설정안해도 됨)
        webDataBinder.addCustomFormatter(new customFormatter());
        // 커스텀 validator (Validator 인터페이스 구현체를 등록)
        // webDataBinder.addValidators(new val());
    }


    // ------------------- 예외 공용처리 -------------------
    @ExceptionHandler(CustomException.class)
    public String mvcErrorHandler(CustomException c, Model model){

        int httpStatus = c.getErrorCode().getHttpStatus().value();
        String errorMessage = c.getErrorCode().getMessage();

        String returnMsg = "[" + httpStatus + "] " + errorMessage;
        System.err.println(returnMsg);

        model.addAttribute("httpStatus", httpStatus);
        model.addAttribute("httpStatus", errorMessage);

        return "errView";
    }

    // ------------- 예외 공용처리(Rest API) ----------------
    /*
    @ExceptionHandler(CustomException.class)
    public ResponseEntity restApiErrorHandler(CustomException c, Model model){

        String errorMessage = c.getErrorCode().getMessage();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("message", errorMessage);

        return new ResponseEntity(model, httpHeaders, c.getErrorCode().getHttpStatus());
    }
    */

    // ------------- 공용으로 적용되는 Model 객채 ------------
    @ModelAttribute
    public void myModel(Model model){
        model.addAttribute("myData", "data");
    }


    // -------------------- test -------------------------
    @GetMapping("/test/{code}")
    public String test(@PathVariable("code") InitBinderDto dto,
                                      Model model){

        System.out.println(model.getAttribute("myData"));
        return dto.toString();
    }

    @GetMapping("/errorTest")
    public String errorTest(){
        throw new CustomException(INVALID_AUTH_TOKEN);
    }
}
