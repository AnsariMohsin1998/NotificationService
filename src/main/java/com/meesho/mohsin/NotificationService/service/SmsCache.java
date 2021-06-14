package com.meesho.mohsin.NotificationService.service;

import com.meesho.mohsin.NotificationService.controller.SmsController;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmsCache {

    // Logger
    private static final Logger log = LoggerFactory.getLogger(SmsCache.class);


    @Autowired
    public SmsRepository smsRepository;


    //@Cacheable(cacheNames = "smsCache")
    public List<SmsRequests> findAll(){
        log.info("FETCHING DATA FROM DATABASE");
        return smsRepository.findAll();
    }

}
