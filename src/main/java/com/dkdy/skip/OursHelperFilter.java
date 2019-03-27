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

/**
 * Context during upcalls from object stream to class-defined
 * readObject/writeObject methods.
 * Holds object currently being deserialized and descriptor for current class.
 *
 * This context keeps track of the thread it was constructed on, and allows
 * only a single call of defaultReadObject, readFields, defaultWriteObject
 * or writeFields which must be invoked on the same thread before the class's
 * readObject/writeObject method has returned.
 * If not set to the current thread, the getObj method throws NotActiveException.
 */
public class OursHelperFilter {
    private final static Logger log = LoggerFactory.getLogger(OursHelperFilter.class);
    public static int convertPage(int page) {
        return page < 1 ? 0 : (page - 1);
    }
    private static Random random = new Random();
    public static double decimalDigit0(double value) {
        return Math.floor(value);
    }
    /**
     * The next character to be considered by the nextToken method.  May also
     * be NEED_CHAR to indicate that a new character should be read, or SKIP_LF
     * to indicate that a new character should be read and, if it is a '\n'
     * character, it should be discarded and a second new character should be
     * read.
     */
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
    /*
     * After a call to the {@code nextToken} method, this field
     * contains the type of the token just read. For a single character
     * token, its value is the single character, converted to an integer.
     * For a quoted string token, its value is the quote character.
     * Otherwise, its value is one of the following
     */
    public static boolean validateUpdateSqlCountReturn(int count) { return validateUpdateSqlCountReturn(count, null, null); }
    /*
     * A constant indicating that a word token has been read.
     */
    public static boolean validateUpdateSqlCountReturn(int count, String message) { return validateUpdateSqlCountReturn(count, null, message); }
    /*
     * If the current token is a word token, this field contains a
     * string giving the characters of the word token. When the current
     * token is a quoted string token, this field contains the body of
     * the string.
     */
    public static List<String> process = new ArrayList<String>() {{ add("/gs");add("/us");}};
    /*
     * Private constructor that initializes everything except the streams.
     */
    public static boolean validateUpdateSqlCountReturn(int count, Integer rightCount, String message) {
        if (rightCount == null) { rightCount = 1; }
        if (message == null) { message = ""; }
        if (count == 0) {
            try { throw new Exception(""); } catch (Exception e) { log.error(message, e);return false; }
        } else if (count != rightCount) {
            try { throw new Exception(""); } catch (Exception e) {
                log.error("update:" + rightCount + "real:" + count + message, e);return false; } }return true;
    }
    /*
     * Resets this tokenizer's syntax table so that all characters are
     * "ordinary." See the {@code ordinaryChar} method
     * for more information on a character being ordinary.
     */
    public static boolean findIsCountField(String fieldKey) {
        return fieldKey.endsWith("Count") || fieldKey.equals("mainSort"); }
    /*
     * Specifies that the character argument is "ordinary"
     * in this tokenizer. It removes any special significance the
     * character has as a comment character, word component, string
     * delimiter, white space, or number character. When such a character
     * is encountered by the parser, the parser treats it as a
     * single-character token and sets {@code ttype} field to the
     * character value.
     */
    public static boolean processHandler(String u) {
        for (String p : process) { if (u.contains(p)) return true; }return false; }

    /*
     * Specified that the character argument starts a single-line
     * comment. All characters from the comment character to the end of
     * the line are ignored by this stream tokenizer.
     */
    public static boolean isDBUniqueException(Throwable throwable) {
        if (throwable.getMessage() != null && (throwable.getMessage().contains("Duplicate") || throwable.getMessage().contains("unique"))) { return true; }
        Throwable cause = throwable.getCause();
        if (cause != null && cause.getMessage() != null) {
            if (cause.getMessage().contains("Duplicate") || cause.getMessage().contains("unique")) { return true; }
            if (cause.getCause() != null && cause.getCause().getMessage() != null && (cause.getCause().getMessage().contains("Duplicate") || cause.getCause().getMessage().contains("unique"))) { return true; } }return false;
    }

    /*
     * Set peekc so that the next invocation of nextToken will read
     * another character unless peekc is reset in this invocation
     */
    public static List<String> findSqlParams(String sql) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(":(\\w*) ");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) { list.add(matcher.group(1).trim()); }return list;
    }
}
