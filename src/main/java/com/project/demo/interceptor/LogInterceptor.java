package com.project.demo.interceptor;

import com.project.demo.utils.MyLog;
import com.project.demo.utils.MySessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Yzl
 * @Date: 2019/11/13 22:58
 * @Description:
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MyLog.info("SessionId:" + MySessionUtil.getSession().getId() + "\tRequest : " + request.getRequestURL().substring(21));
        return true;
    }
}
