package com.project.demo.utils;

import com.sun.istack.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/9 12:28
 */
public class DecimalUtil {
    private static BigDecimal bigDecimal;
    private final static DecimalFormat decimalFormat = new DecimalFormat("#.00000");

    public static String avg(@NotNull Integer... integer) {
        if (integer == null) throw new NullPointerException();
        return getResult(integer);
    }

    public static String avg(@NotNull Float... integer) {
        if (integer == null) throw new NullPointerException();
        return getResult(integer);
    }

    private static synchronized String getResult(Object[] objects) {
        if (objects[0] instanceof Number) {
            double result = 0;
            for (Number i : (Number[]) objects) {
                result += i.doubleValue();
            }
            bigDecimal = new BigDecimal(result);
            bigDecimal = bigDecimal.divide(new BigDecimal(objects.length), BigDecimal.ROUND_HALF_UP);
            if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                return "0.00";
            } else if (bigDecimal.compareTo(BigDecimal.ZERO) > 0 && bigDecimal.compareTo(new BigDecimal(1)) < 0) {
                return "0" + decimalFormat.format(bigDecimal).toString();
            } else {
                return decimalFormat.format(bigDecimal).toString();
            }
        } else {
            throw new ClassCastException();
        }
    }
}
