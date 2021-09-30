package com.example.webmvc_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class MappingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    ObjectMapper objectMapper;


    @Before("")
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    public void pathVariableTest() throws Exception {
        mockMvc.perform(get("/pathVariable/sun"))
                .andDo(print());
    }

    @Test
    public void matrix1() throws Exception{

        mockMvc.perform(get("/matrix1/sun1;data2=123"))
                .andDo(print());
    }

    @Test
    public void matrix2()  throws Exception{
        mockMvc.perform(get("/matrix2/data1=sun/data2=123"))
                .andDo(print());
    }

    @Test
    public void matrix3()  throws Exception{
        mockMvc.perform(get("/matrix3/data1=sun;data2=123"))
                .andDo(print());
    }
}