package com.meesho.mohsin.NotificationService.service;

import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsService {

    @Autowired
    public SmsRepository smsRepository;

    public SmsService(){
        System.out.println("SmsService is created !");
    }

    public List<SmsRequests> findAllSmsRequests(){
        return smsRepository.findAll();
    }

    public SmsRequests findSmsRequestById(int id){
        return smsRepository.findById(id);
    }

}
