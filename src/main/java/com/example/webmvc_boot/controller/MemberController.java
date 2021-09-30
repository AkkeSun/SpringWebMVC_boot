package com.example.webmvc_boot.controller;

import com.example.webmvc_boot.dto.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;


@Log4j2
@Controller
@SessionAttributes("memberDto")
public class MemberController {

    private static final String VIEWS_LOGIN_PAGE = "loginPage";

    @ModelAttribute
    public void category (Model model){
        model.addAttribute("category", List.of("study", "sport", "game"));
    }

    @GetMapping("/member/login")
    public String getLogin(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return VIEWS_LOGIN_PAGE;
    }

    @PostMapping("/member/login")
    public String postLogin(@Validated MemberDto member,
                            BindingResult result){

        if(result.hasErrors())
            return VIEWS_LOGIN_PAGE;

        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(){
        return "listView";
    }
}
