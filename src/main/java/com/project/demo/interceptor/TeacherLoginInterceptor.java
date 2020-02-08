package com.project.demo.interceptor;

import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import com.project.demo.utils.MySessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/8 20:03
 */
@Component
public class TeacherLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer teacherId = (Integer) MySessionUtil.getSession().getAttribute(MySessionUtil.USER_ID);
        if (teacherId == null) {
            throw new BusinessException(EmBusinessErr.NOT_LOGIN_USER);
        }
        return true;
    }
}
