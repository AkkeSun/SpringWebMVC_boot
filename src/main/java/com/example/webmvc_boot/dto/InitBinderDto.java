package com.example.webmvc_boot.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class InitBinderDto {

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate date1;
    private String myData1;
    private String myData2;
}
