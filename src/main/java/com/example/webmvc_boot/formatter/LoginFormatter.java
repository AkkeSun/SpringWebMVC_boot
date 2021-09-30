package com.example.webmvc_boot.formatter;

import com.example.webmvc_boot.dto.MemberDto;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class LoginFormatter implements Formatter<MemberDto> {

    @Override
    public MemberDto parse(String s, Locale locale) throws ParseException {
        MemberDto dto = new MemberDto();
        dto.setMyId(s);
        return dto;
    }

    @Override
    public String print(MemberDto memberDto, Locale locale) {
        return null;
    }
}
