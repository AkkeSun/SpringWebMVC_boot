package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RestController
public class MappingController {

    // http://localhost:8080/pathVariable/sun
    @GetMapping("pathVariable/{dto}")
    public String pathVariableTest (@PathVariable MemberDto dto){
        String resultMsg = "pathVariable = "+dto.getMyId();
        return resultMsg;
    }

    // http://localhost:8080/matrix1/sun;data2=123
    @GetMapping("matrix1/{data1}")
    public String matrix1(@PathVariable String data1
                        , @MatrixVariable String data2){
        String resultMsg = "matrix1 = "+ data1 + " " + data2;
        return resultMsg;
    }

    // http://localhost:8080/matrix2/data1=sun/data2=123
    @GetMapping("/matrix2/{data1}/{data2}")
    public String matrix2(@MatrixVariable(pathVar="data1") String data1
                        , @MatrixVariable(pathVar="data2") String data2) {
        String resultMsg = "matrix2 = "+ data1 + " " + data2;
        return resultMsg;
    }

    // http://localhost:8080/matrix3/data1=sun;data2=123
    @GetMapping("/matrix3/{data}")
    public HashMap matrix3(@MatrixVariable(pathVar="data") HashMap<String, String> data) {
        return data;
    }
}
