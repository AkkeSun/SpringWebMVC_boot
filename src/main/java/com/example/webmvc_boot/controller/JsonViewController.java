package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.JsonViewDto;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonViewController {

    @GetMapping("JsonViewTest")
    @JsonView(JsonViewDto.view2.class) // 출력하고자 하는 인터페이스 지정
    public JsonViewDto jsonviewTest(){
        JsonViewDto dto  = new JsonViewDto();
        dto.setData1("d1");
        dto.setData2("d2");
        dto.setData3("d3");
        dto.setData4("d4");
        dto.setData5("d5");
        return dto;
    }
}
