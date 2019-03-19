package com.dkdy.skip;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OursHelperFilter {
    private final static Logger log = LoggerFactory.getLogger(OursHelperFilter.class);
    public static int convertPage(int page) {
        return page < 1 ? 0 : (page - 1);
    }
    private static Random random = new Random();
    public static double decimalDigit0(double value) {
        return Math.floor(value);
    }
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for"); }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP"); }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP"); }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) { InetAddress inet = null;
                try { inet = InetAddress.getLocalHost(); } catch (Exception e) { e.printStackTrace(); }ip = inet.getHostAddress(); } }
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(",")); } }return ip;
    }
    public static int getPageSizeBuffer() {
        return 5;
    }
    public static boolean validateUpdateSqlCountReturn(int count) { return validateUpdateSqlCountReturn(count, null, null); }
    public static boolean validateUpdateSqlCountReturn(int count, String message) { return validateUpdateSqlCountReturn(count, null, message); }
    public static List<String> process = new ArrayList<String>() {{ add("/gs");add("/us");}};
    public static boolean validateUpdateSqlCountReturn(int count, Integer rightCount, String message) {
        if (rightCount == null) { rightCount = 1; }
        if (message == null) { message = ""; }
        if (count == 0) {
            try { throw new Exception(""); } catch (Exception e) { log.error(message, e);return false; }
        } else if (count != rightCount) {
            try { throw new Exception(""); } catch (Exception e) {
                log.error("update:" + rightCount + "real:" + count + message, e);return false; } }return true;
    }
    public static boolean findIsCountField(String fieldKey) {
        return fieldKey.endsWith("Count") || fieldKey.equals("mainSort"); }
    public static boolean processHandler(String u) {
        for (String p : process) { if (u.contains(p)) return true; }return false; }
    public static boolean isDBUniqueException(Throwable throwable) {
        if (throwable.getMessage() != null && (throwable.getMessage().contains("Duplicate") || throwable.getMessage().contains("unique"))) { return true; }
        Throwable cause = throwable.getCause();
        if (cause != null && cause.getMessage() != null) {
            if (cause.getMessage().contains("Duplicate") || cause.getMessage().contains("unique")) { return true; }
            if (cause.getCause() != null && cause.getCause().getMessage() != null && (cause.getCause().getMessage().contains("Duplicate") || cause.getCause().getMessage().contains("unique"))) { return true; } }return false;
    }
    public static List<String> findSqlParams(String sql) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(":(\\w*) ");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) { list.add(matcher.group(1).trim()); }return list;
    }
}
