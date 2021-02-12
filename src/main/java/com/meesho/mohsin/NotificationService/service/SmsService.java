package com.meesho.mohsin.NotificationService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.request.MessageRequestBody;
import com.meesho.mohsin.NotificationService.model.request.PhoneNumberRequestBody;
import com.meesho.mohsin.NotificationService.model.response.BlackListResponse;
import com.meesho.mohsin.NotificationService.model.response.SuccessMessageResponse;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository2;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import com.meesho.mohsin.NotificationService.repository.cache.SmsCacheRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SmsService {


    @Autowired
    public SmsRepository smsRepository;

    @Autowired
    public SmsCacheRepository smsCacheRepository;

    @Autowired
    public SmsCache smsCache;

    @Autowired
    public BlackListedRepository blackListedRepository;

    @Autowired
    public BlackListedRepository2 blackListedRepository2;

    @Autowired
    public KafkaProducerService kafkaProducerService;

    @Autowired
    ObjectMapper objectMapper;

    public SmsService(){
        System.out.println("SmsService is created !");
    }

    public List<SmsDto> findAllSmsRequests(){

        // ONE BY ONE fetch by id

        List<SmsRequests> smsRequests = smsCacheRepository.findAll();
        List<SmsDto> smsDtos = new ArrayList<>();

        for(SmsRequests x : smsRequests){
            SmsDto smsDto = new SmsDto();

            BeanUtils.copyProperties(x,smsDto);

            smsDtos.add(smsDto);
        }
        return smsDtos;
    }

    public SuccessMessageResponse sendMessage(MessageRequestBody messageRequestBody) throws JsonProcessingException {

        SmsRequests sms = new SmsRequests();
        sms.setPhone_number(messageRequestBody.getPhone_number());
        sms.setMessage(messageRequestBody.getMessage());
        sms.setStatus("OK");
        SmsRequests smsRequestSaved = smsRepository.save(sms);

        smsCacheRepository.refreshCache(Collections.singletonList(smsRequestSaved.getId()));

        kafkaProducerService.sendMsg(String.valueOf(sms.getId()));

        SuccessMessageResponse successMessageResponse = new SuccessMessageResponse();
        successMessageResponse.setRequest_id(String.valueOf(sms.getId()));
        successMessageResponse.setComments("Successfully Stored in Mysql");

        return successMessageResponse;
    }

    public SmsDto findSmsRequestById(int id) throws JsonProcessingException {
        SmsDto smsDto = new SmsDto();

        SmsRequests smsRequest = smsCacheRepository.findById(id);
        BeanUtils.copyProperties(smsRequest,smsDto);

        return smsDto;
    }

    public SuccessMessageResponse addToBlackList(List<String> phoneNumbers){
        for(String pno : phoneNumbers){
            BlackListedEntity blackListedEntity = new BlackListedEntity();
            blackListedEntity.setPhone_number(pno);
            //saves in db
            blackListedRepository2.save(blackListedEntity);
            //saves in cache
            blackListedRepository.save(pno);
        }
        SuccessMessageResponse successMessageResponse = new SuccessMessageResponse();
        successMessageResponse.setMessage("Successfully Blacklisted");
        return successMessageResponse;
    }

    public SuccessMessageResponse deleteFromBlackList(String phone_number){
        blackListedRepository.delete(phone_number);
        blackListedRepository2.deleteById(phone_number);
        SuccessMessageResponse successMessageResponse = new SuccessMessageResponse();
        successMessageResponse.setMessage("successfully deleted "+phone_number+" from blacklist");
        return successMessageResponse;
    }

    public BlackListResponse getBlackList(){

        Set<String> blackListResponseFromCache = blackListedRepository.findAll();

        if(blackListResponseFromCache == null){
            //fetch from database and put in cache
            List<BlackListedEntity> blackListedEntities = blackListedRepository2.findAll();
            List<String> phoneNumbers = new ArrayList<>();
            for(BlackListedEntity blackListedEntity : blackListedEntities)
                phoneNumbers.add(blackListedEntity.getPhone_number());

            addToBlackList(phoneNumbers);
        }

        BlackListResponse blackListResponse = new BlackListResponse();
        blackListResponse.setData(blackListResponseFromCache);
        return blackListResponse;

    }

}
