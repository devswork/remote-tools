package com.github.devswork.interceptor;

import com.alibaba.fastjson.JSON;
import com.dkdy.skip.OursHelperFilter;

import com.github.devswork.util.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * The <code>DataOutput</code> interface provides
 * for converting data from any of the Java
 * primitive types to a series of bytes and
 * writing these bytes to a binary stream.
 * There is  also a facility for converting
 * a <code>String</code> into
 * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
 * format and writing the resulting series
 * of bytes.
 * <p>
 * For all the methods in this interface that
 * write bytes, it is generally true that if
 * a byte cannot be written for any reason,
 * an <code>IOException</code> is thrown.
 *
 */

@Slf4j
@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

    /**
     * Writes to the output stream all the bytes in array <code>b</code>.
     * If <code>b</code> is <code>null</code>,
     * a <code>NullPointerException</code> is thrown.
     * If <code>b.length</code> is zero, then
     * no bytes are written. Otherwise, the byte
     * <code>b[0]</code> is written first, then
     * <code>b[1]</code>, and so on; the last byte
     * written is <code>b[b.length-1]</code>.
     *
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (OursHelperFilter.processHandler(request.getRequestURI())) return true;
            Enumeration<String> headerNames = request.getHeaderNames();
            Map<String, Object> headerMap = new HashMap<>(10);
            do {
                String header = headerNames.nextElement();
                headerMap.put(header, request.getHeader(header));
            } while (headerNames.hasMoreElements());
            log.info("\n" +
                    "ADDRESS\t\t\t->\t\t{}\n" +
                    "METHOD\t\t\t->\t\t{}\n" +
                    "PARAMS\t\t\t->\t\t{}\n" +
                    "FROM\t\t\t->\t\t{}\n" +
                    "HEADER\t\t\t->\t\t{}\n" +
                    "CONTENT-TYPE\t\t\t->\t\t{}\n",
                    request.getRequestURI(),
                    request.getMethod(),
                    JSONUtil.toJSON(request.getParameterMap()),
                    request.getRemoteAddr(),
                    JSON.toJSONString(headerMap),
                    request.getContentType());
        } catch (Exception e) { }
        return true;
    }

    /**
     * Writes to the output stream the eight low-
     * order bits of the argument <code>v</code>.
     * The 24 high-order bits of <code>v</code>
     * are ignored. (This means  that <code>writeByte</code>
     * does exactly the same thing as <code>write</code>
     * for an integer argument.) The byte written
     * by this method may be read by the <code>readByte</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>byte</code>
     * equal to <code>(byte)v</code>.
     *
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
