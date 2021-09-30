package com.example.webmvc_boot.formatter;

import com.example.webmvc_boot.dto.InitBinderDto;
import com.example.webmvc_boot.dto.MemberDto;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class customFormatter implements Formatter<InitBinderDto> {

    @Override
    public InitBinderDto parse(String code, Locale locale) throws ParseException {
        InitBinderDto dto = new InitBinderDto();
        if(code.equals("code1")){
            dto.setMyData1("code1_Data1");
            dto.setMyData2("code1_Data2");
        }
        else if(code.equals("code2")){
            dto.setMyData1("code2_Data1");
            dto.setMyData2("code2_Data2");
        }
        return dto;
    }

    @Override
    public String print(InitBinderDto dto, Locale locale) {
        return null;
    }
}
