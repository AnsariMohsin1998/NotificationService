package com.meesho.mohsin.NotificationService.config;

import com.meesho.mohsin.NotificationService.service.InterceptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Component
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    InterceptorService interceptorService;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorService);
    }
}
