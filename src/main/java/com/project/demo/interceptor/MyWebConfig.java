package com.project.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Ck
 * 拦截器配置类
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Resource
    private LogInterceptor logInterceptor;
//    @Resource
//    private UserLoginInterceptor userLoginInterceptor;
//
//    @Resource
//    private AdminLoginInterceptor adminLoginInterceptor;

    private final String[] userLoginPath = {"/student/**"};
    private final String[] userLoginExcludePath = {"/student/studentLogin", "/student/studentRegistered"};
    private final String[] adminLoginPath = {"/teacher/**"};
    private final String[] adminLoginExcludePath = {"/teacher/teacherLogin", "/teacher/teacherRegistered"};

    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        注册日志信息
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(userLoginInterceptor).addPathPatterns(userLoginPath).excludePathPatterns(userLoginExcludePath);
//        registry.addInterceptor(adminLoginInterceptor).addPathPatterns(adminLoginPath).excludePathPatterns(adminLoginExcludePath);
    }
}
