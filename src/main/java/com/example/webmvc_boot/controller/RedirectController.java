package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@SessionAttributes("memberDto")
public class RedirectController {

    /*--------------------------------
    리다이렉트 시 세션 전체가 아니라,
    원하는 값만 보내고 싶을 때 사용

    redirectAttributes.addAttribute
    리다이렉트 시 데이터를 get방식으로 보낸다

    redirectAttributes.addFlashAttribute
    리다이렉트 시 데이터를 post방식으로 보낸다

    --------------------------------- */

    //--------------아이디 입력--------------
    @GetMapping("/redirect/getId")
    public String getId(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "redirect/id";
    }

    @PostMapping("/redirect/postId")
    public String postId(@Validated(MemberDto.group1.class) MemberDto memberDto,
                         BindingResult result){

        if(result.hasErrors())
            return "redirect/id";

        return "redirect:/redirect/getPwd";
    }

    //--------------비밀번호 입력--------------
    @GetMapping("/redirect/getPwd")
    public String getPwd(Model model, MemberDto memberDto){
        model.addAttribute("memberDto", memberDto);
        return "redirect/pwd";
    }

    @PostMapping("/redirect/postPwd")
    public String postPwd(@Validated(MemberDto.group2.class) MemberDto memberDto,
                          SessionStatus sessionStatus,
                          BindingResult result,
                          RedirectAttributes redirectAttributes){

        if(result.hasErrors())
            return "redirect/pwd";

        redirectAttributes.addAttribute("id", memberDto.getMyId());
        redirectAttributes.addFlashAttribute("pwd", memberDto.getMyPwd());
        sessionStatus.setComplete();
        return "redirect:/redirect/list";
    }

    @ResponseBody
    @GetMapping("/redirect/list")
    public String list(Model model, String id){
        
        //addAttribute 받기
        log.info("addAttribute Data : " + id);
        
        //addFlashAttribute 받기
        String postData = (String) model.asMap().get("pwd");
        log.info("addFlashAttibute Data : " +postData);

        return "listView";
    }
}
