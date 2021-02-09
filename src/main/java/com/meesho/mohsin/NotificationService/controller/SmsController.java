package com.meesho.mohsin.NotificationService.controller;


import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;


    @GetMapping(value = "/sms")
    public List<SmsRequests> getAllSms(){
        return smsService.findAllSmsRequests();
    }

    @GetMapping(value = "/sms/{id}")
    public SmsRequests findSmsById(@PathVariable(value = "id") int id){
        return smsService.findSmsRequestById(id);
    }
}
