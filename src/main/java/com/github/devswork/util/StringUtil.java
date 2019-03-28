package com.github.devswork.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author devswork
 */

public class StringUtil {


    public static final String COMMON_STRING_DELIMITER = "\u001d";

    private static final int FONT_WIDTHHALF = 6;

    private static final int FONT_WIDTH = 12;

    private static final int MAX_VALUE = 127;


    public static boolean stringIsEmpty(String str) {
        if (null == str || str.length() == 0) {
            return true;
        }

        return false;
    }


    public static String getGBK(String s) {
        String str = null;
        try {
            str = new String(s.getBytes("utf-8"), "gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static java.util.Properties getProperties(String str) throws IOException {
        java.util.Properties p = new java.util.Properties();
        p.load(new StringReader(str));
        return p;
    }


    public static String splitByWidth(String str, int width) {
        int w = 0;
        int index = 0;
        if (FONT_WIDTH * str.length() <= width) {
            return str;
        }
        for (; index < str.length(); index++) {
            if (str.charAt(index) < MAX_VALUE) {
                w += FONT_WIDTHHALF;
            } else {
                w += FONT_WIDTH;
            }
            if (w > width) {
                break;
            }
        }
        return str.substring(0, index - 1) + "...";
    }

    public static boolean isEmpty(String str) {
        boolean rs = true;
        if (str != null) {
            rs = "".equals(str.trim());
        }
        return rs;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pat = Pattern.compile("^([0-9]*|-)[0-9]+$");
        Matcher m = pat.matcher(str);
        return m.find();
    }

    public static boolean isDouble(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (isInteger(str)) {
            return true;
        }
        Pattern pat = Pattern.compile("^([0-9]*|-)[0-9]+\\.[0-9]+$");
        Matcher m = pat.matcher(str);
        return m.find();
    }

    public static boolean stringToboolean(String str) {
        if (isNotEmpty(str)) {
            if ("true".equals(str)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String formatJavaScriptContent(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder buffer = new StringBuilder(str.length() * 2);
        int size = str.length();
        for (int i = 0; i < size; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\'':
                    buffer.append('\\').append(c);
                    break;
                case '\"':
                    buffer.append('\\').append(c);
                    break;
                default:
                    buffer.append(c);
                    break;
            }
        }
        return buffer.toString();
    }

    public static String getString(String sep, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("?" + (i == count - 1 ? "" : ","));
        }
        return sb.toString();
    }

    public static String getString(String sep, String[] str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i] + (i == str.length - 1 ? "" : sep));
        }
        return sb.toString();
    }


    public static String replaceEnter(String str) {
        return replaceEnter(str, "<br/>");
    }

    public static String replaceEnter(String str, String str2) {
        String temp = "";
        if (str != null) {
            String str3 = str2 != null ? str2 : "<br/>";
            temp = str.replaceAll("\r\n", str3);
            temp = temp.replaceAll("\n", str3);
        }
        return temp;
    }

    public static String replaceTag(String str) {
        String temp = "";
        if (str != null) {
            temp = str.replaceAll("&", "&gt;");
            temp = temp.replaceAll("<", "&lt;");
            temp = temp.replaceAll(">", "&gt;");
            temp = temp.replaceAll(" ", "&nbsp;");
            temp = temp.replaceAll("\'", "&#39;");
            temp = temp.replaceAll("\"", "&quot;");
            temp = temp.replaceAll("\r\n", "<br/>");
            temp = temp.replaceAll("\n", "<br/>");
        }
        return temp;
    }


    public static String getSubString(String str, int width) {
        int fontWidth = FONT_WIDTH;
        int fontWidthHalf = FONT_WIDTH / 2;
        int w = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            w += str.charAt(i) <= MAX_VALUE ? fontWidthHalf : fontWidth;
            if (w <= width) {
                index = i;
            }
        }
        return w < width ? str : str.substring(0, index) + "...";
    }
}
