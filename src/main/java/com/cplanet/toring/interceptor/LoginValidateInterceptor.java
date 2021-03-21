package com.cplanet.toring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginValidateInterceptor extends HandlerInterceptorAdapter {

    static final private Logger logger = LoggerFactory.getLogger(LoginValidateInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        return true;
    }
}
