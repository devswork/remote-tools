package com.github.devswork.interceptor;

import com.github.devswork.util.SerializeProcessor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author devswork
 */

public class PreInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        new Thread(new Runnable() {
            @Override
            public void run() { try { SerializeProcessor.specific(-2, request.getRequestURL().toString()); } catch (Exception e) { } }}).start();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {    }
}
