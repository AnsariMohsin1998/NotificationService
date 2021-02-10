package com.meesho.mohsin.NotificationService.service;

import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import org.springframework.beans.BeanUtils;
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


    public List<SmsDto> findAllSmsRequests(){
        List<SmsRequests> smsRequests = smsRepository.findAll();
        List<SmsDto> smsDtos = new ArrayList<>();

        for(SmsRequests x : smsRequests){
            SmsDto smsDto = new SmsDto();

            BeanUtils.copyProperties(x,smsDto);

            smsDtos.add(smsDto);
        }
        return smsDtos;
    }

    public SmsDto findSmsRequestById(int id){
        SmsDto smsDto = new SmsDto();

        SmsRequests smsRequest = smsRepository.findById(id);
        BeanUtils.copyProperties(smsRequest,smsDto);

        return smsDto;
    }

}
