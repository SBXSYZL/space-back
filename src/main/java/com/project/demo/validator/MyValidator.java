package com.project.demo.validator;

import com.alibaba.druid.util.StringUtils;
import com.project.demo.error.BusinessException;
import com.project.demo.error.CommonError;
import com.project.demo.error.EmBusinessErr;
import org.springframework.validation.BindingResult;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/10 19:28
 */
public class MyValidator {
    public static void checkTel(String tel) throws BusinessException {
        String pattern = "^[1][34578][0-9]{9}$";
        if (!Pattern.matches(pattern, tel)) {
            throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR, "手机号不合法");
        }
    }

    public static void checkPassword(String password) throws BusinessException {
        if (password.trim().length() < 6) {
            throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR, "密码长度太短");
        } else if (password.trim().length() > 12) {
            throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR, "密码长度太长");
        }
    }

    public static void checkStrNull(String... array) throws BusinessException {
        for (String var : array) {
            if (var == null || StringUtils.isEmpty(var.trim())) {
                throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR);
            }
        }
    }

    /**
     * 检查Integer是否为null或小于0
     * 如果是，则抛出参数不合法异常
     */
    public static void checkIntNull(Integer... array) throws BusinessException {
        for (Integer var : array) {
            if (var == null || var < 0) {
                throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR);
            }
        }
    }

    /**
     * 检查Object是否为null，用于校验其他类型，例如Boolean
     * 如果是，则抛出参数不合法异常
     */
    public static void checkObjectNull(Object... objects) throws BusinessException {
        for (Object var : objects) {
            if (var == null) {
                throw new BusinessException(EmBusinessErr.PARAMETER_INVALIDATION_ERROR);
            }
        }
    }


    /**
     * 用于获取BindingResult校验结果
     */
    public static void validateObject(CommonError error, BindingResult result) throws BusinessException {
        if (result.hasErrors()) {
            String errMsg = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
            throw new BusinessException(error, errMsg);
        }
    }
}
