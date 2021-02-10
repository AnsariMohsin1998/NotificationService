package com.meesho.mohsin.NotificationService.controller;


import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping(value = "/get/bulk")
    public List<SmsDto> getAllSms(){
        return smsService.findAllSmsRequests();
    }

    @GetMapping(value = "/get/{id}")
    public SmsDto findSmsById(@PathVariable(value = "id") int id){
        return smsService.findSmsRequestById(id);
    }

}