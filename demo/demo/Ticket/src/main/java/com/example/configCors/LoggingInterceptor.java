package com.example.configCors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //Log request details here
//        log.info("Request URL: " + request.getRequestURL().toString());
//        log.info("Request Method: " + request.getMethod());
//        log.info("Request Authorization: " + request.getHeader("Authorization"));
//        log.info("Request IP: " + request.getRemoteAddr());
        return true;
    }
}
