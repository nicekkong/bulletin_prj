package com.cplanet.toring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(value = "/error")
public class ErrorController {

    static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    public void checkError(HttpServletRequest request, HttpServletResponse response) {

        logger.error("request ::: " , request);
        logger.error("response ::: " , response);


    }
}
