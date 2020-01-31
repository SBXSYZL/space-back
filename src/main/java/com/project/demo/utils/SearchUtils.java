package com.project.demo.utils;

/**
 * @Auther: Yzl
 * @Date: 2019/10/4 13:02
 * @Description:
 */
public class SearchUtils {
    public static String blurring(String key) {
        String regex = "(.{1})";
        String result = key.replaceAll(regex, "$1%");
        result = "%".concat(result);
        return result;
    }

    public static String simpleBlurring(String key) {
        StringBuilder builder = new StringBuilder("");
        builder.append('%');
        builder.append(key);
        builder.append('%');
        return builder.toString();
    }

    public static void main(String[] args) {
        String s = simpleBlurring("123");
        System.out.println(s);
    }
}