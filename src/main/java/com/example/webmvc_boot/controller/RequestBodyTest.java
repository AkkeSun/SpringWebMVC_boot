package com.example.webmvc_boot.controller;


import com.example.webmvc_boot.dto.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
public class RequestBodyTest {

    /*

    @RequestBody
    body 데이터를 HttpMessageConvertor로 객채 변환하여 받아들이는 것

    HttpEntity
    @RequestBody와 거의 동일하지만 body 뿐 아니라
    header 정보까지 접근 가능하다

    ResponseEntity
    @ResponseBody와 거의 동일하지만 body뿐 아니라
    status, header 정보도 보내줄 수 있다.

     */
    @ResponseBody
    @PostMapping("/requestBodyTest")
    public MemberDto requestBodyTest(@RequestBody MemberDto dto){
        return dto;
    }

    @ResponseBody
    @PostMapping("/httpEntityTest")
    public MemberDto httpEntityTest(HttpEntity<MemberDto> req){
        log.info(req.getHeaders());
        return req.getBody();
    }

    @GetMapping("/responseEntityTest")
    public ResponseEntity<MemberDto> entityTest(@RequestBody MemberDto dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("age", "31");

        return new ResponseEntity<MemberDto>(dto, httpHeaders, HttpStatus.OK);
    }
}
