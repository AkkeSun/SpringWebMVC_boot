package com.example.webmvc_boot.dto;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class JsonViewDto {
    // 언제 출력할지 셋팅
    public interface view1{};
    public interface view2{};

    @JsonView({view1.class, view2.class})
    private String data1;

    @JsonView(view1.class)
    private String data2;

    @JsonView(view1.class)
    private String data3;

    @JsonView(view2.class)
    private String data4;

    @JsonView(view2.class)
    private String data5;
}
