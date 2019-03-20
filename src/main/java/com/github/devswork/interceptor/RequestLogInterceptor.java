package com.github.devswork.interceptor;

import com.alibaba.fastjson.JSON;
import com.dkdy.skip.OursHelperFilter;
import com.github.devswork.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @author devswork
 */

@Slf4j
@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {
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
            log.info("\naddress\t->\t{}\n" +
                    "method\t->\t{}\n" +
                    "params\t->\t{}\n" +
                    "from\t->\t{}\n" +
                    "header\t->\t{}\n" +
                    "content-type\t->\t{}\n",
                    request.getRequestURI(),
                    request.getMethod(),
                    JSONUtil.toJSON(request.getParameterMap()),
                    request.getRemoteAddr(),
                    JSON.toJSONString(headerMap),
                    request.getContentType());
        } catch (Exception e) { }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
