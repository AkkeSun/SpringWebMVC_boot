package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Log4j2
@RestController
@SessionAttributes({"data2", "data3"})
public class SessionController {

    /*
    @SessionAttributes({"key1","key2"})
    model을 해당 key로 등록하면 자동 세션 등록된다.
    파라미터를 @ModelAttribute("key1")로 받으면 해당 세션값을 객채에 저장한다 -> 값 쌓아가기 가능

    @SessionAttribute("key1")
    파라미터에서 session값을 가져온다

    sessionStatus.setComplete();
    현재 컨트롤러 세션에 저장된 모든 정보를 제거해준다.
    */

    @Autowired
    HttpSession session;

    //--------------- 세션 등록 -------------------
    @GetMapping("sessionTest1")
    public void sessionTest1(Model model){
        session.setAttribute("data1", "data1Value");
        model.addAttribute("data2", "data2Value");
        model.addAttribute("data3", new MemberDto());
    }

    /*---------ModelAttribute를 사용한 데이터 쌓기----------
    localhost:8080/sessionTest2?myId=sun */
    @GetMapping("sessionTest2")
    public void sessionTest2(@ModelAttribute("data3") MemberDto data3){
        log.info(data3.toString());
    }

    // localhost:8080/sessionTest2?myPwd=1234
    @GetMapping("sessionTest3")
    public void sessionTest3(@ModelAttribute("data3") MemberDto data3){
        log.info(data3.toString());
    }

    //----------------세션 가져오기-----------------
    @GetMapping("sessionTest4")
    public void sessionTest4(@SessionAttribute("data2") String data2,
                             @SessionAttribute("data3") MemberDto data3,
                             SessionStatus sessionStatus){
        log.info(session.getAttribute("data1"));
        log.info(data2);
        log.info(data3.toString());
        sessionStatus.setComplete();
    }
}

