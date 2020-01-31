package com.project.demo.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class MyExceptionUtil {
    public static String getErrorMsg(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
