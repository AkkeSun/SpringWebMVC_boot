package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
public class SessionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void sessionTest1() throws Exception{
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        MockHttpServletRequest request = mockMvc.perform(get("/sessionTest1").param("str", "data1Value"))
                                            .andReturn().getRequest();
        Object obj = request.getSession().getAttribute("data1");
    }


    @Test
    public void sessionTest2() throws  Exception {
        MockHttpServletRequest request = mockMvc.perform(get("/sessionTest2").param("myId","sun"))
                .andReturn().getRequest();
        Object obj = request.getSession().getAttribute("dto");

    }
}