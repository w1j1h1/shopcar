package com.project.shopcar.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogUtil {

    private final static Logger logger = LoggerFactory.getLogger("ROOT");

    public static String exceptionLog(Exception exception) {
        if (exception == null) {
            return "exception is null";
        }
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        exception.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        logger.error(info.toString());
        return info.toString();
    }

    public static String exceptionLog(Exception exception, Class className) {
        if (exception == null) {
            return "exception is null";
        }
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        exception.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        LoggerFactory.getLogger(className).error(info.toString());
        return info.toString();
    }

    public static void info(String string, Class className) {
        LoggerFactory.getLogger(className).info(string);
    }

    public static void info(String string) {
        logger.info(string);
    }
}
