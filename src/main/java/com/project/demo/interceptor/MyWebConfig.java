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

    @Resource
    private TeacherLoginInterceptor teacherLoginInterceptor;

    private final String[] studentLoginPath = {"/student/**"};
    private final String[] studentLoginExcludePath = {"/student/studentLogin", "/student/studentRegistered"};
    private final String[] teacherLoginPath = {"/teacher/**"};
    private final String[] teacherLoginExcludePath = {"/teacher/teacherLogin", "/teacher/teacherRegistered"};

    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        注册日志信息
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        registry.addInterceptor(teacherLoginInterceptor).addPathPatterns(teacherLoginPath).excludePathPatterns(teacherLoginExcludePath);
//        registry.addInterceptor(adminLoginInterceptor).addPathPatterns(adminLoginPath).excludePathPatterns(adminLoginExcludePath);
    }
}
