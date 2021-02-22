package com.meesho.mohsin.NotificationService.service;

import com.meesho.mohsin.NotificationService.constants.InterceptorConstants;
import com.meesho.mohsin.NotificationService.controller.SmsController;
import org.apache.http.HttpStatus;
import org.apache.kafka.common.errors.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class InterceptorService implements HandlerInterceptor {



    private static final Logger log = LoggerFactory.getLogger(InterceptorService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        System.out.println("inside prehandle auth key is "+request.getHeader("Authorization"));
        String authKey = request.getHeader("Authorization");
        if(authKey.contentEquals(InterceptorConstants.authKey)) {
            System.out.println("Key is correct");
            return true;
        }
        else{
            System.out.println("Key is incorrect");
            throw new RuntimeException("Key is Incorrect");
        }
    }

}
