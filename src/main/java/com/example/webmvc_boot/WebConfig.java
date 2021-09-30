package com.example.webmvc_boot;

import com.example.webmvc_boot.Interceptor.LoginCheck;
import com.example.webmvc_boot.formatter.LoginFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.time.format.DateTimeFormatter;


@EnableAspectJAutoProxy
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // DispatcherServlet이 처리하지 못한 요청을 DefaultServlet에게 넘겨주는 역할
    // 서버에 파일을 업로드하는 경우 선언해준다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        WebMvcConfigurer.super.configureDefaultServletHandling(configurer);
    }

    // MatrixVariable 사용을 위한 세미콜롬 허용
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheck())
                .addPathPatterns("/member/*")
                .excludePathPatterns("/member/login")
                .order(0);
    }

    /*
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        // 해당 패턴을 yyyy-MM-dd로 로드하겠다.
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        registrar.registerFormatters(registry);
    }
     */
}
