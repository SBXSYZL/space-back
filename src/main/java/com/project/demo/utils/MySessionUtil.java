package com.project.demo.utils;

import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author Zjp
 * 操作session相关功能的类
 * 2019/04/30
 */
public final class MySessionUtil {

    public static String USER_ID = "id: ";

//    static {
//        MySessionUtil.getSession().setAttribute(USER_ID, 8);
//    }

    /**
     * @author Zjp
     * 获取request对象
     */
    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * @author Zjp
     * 获取response对象
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * @author Zjp
     * 获取session对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * @author Zjp
     * 获取session中的属性值
     */
    public static Object getAttribute(String key) throws BusinessException {
        HttpSession session = getSession();
        if (session.getAttribute(key) == null) {
            String method_name = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名
            MyLog.error(method_name + "\t" + "session没有\"" + key + "\"属性");
            throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR, "session没有\"" + key + "\"属性");
        }
        return session.getAttribute(key);
    }

    /**
     * @author Zjp
     * 在session中存入值
     */
    public static void setAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * @author Zjp
     * 删除session中的属性
     */
    public static void removeAttribute(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * @author Zjp
     * 获取服务器路径
     */
    static String getRealPath() {
        return getSession().getServletContext().getRealPath("/");
    }

}
