package com.example.webmvc_boot.Interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Log4j2
public class LoginCheck implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 객채가 있으면 객채를 반환, 없으면 false를 반환
        HttpSession session = request.getSession();
        Object obj = request.getSession().getAttribute("login");

       if(obj == null){
           log.info("==========Not Login===========");
           response.sendRedirect("/member/login");
           return false;
       }

       return true;
    }
}