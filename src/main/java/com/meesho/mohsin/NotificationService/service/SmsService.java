package com.meesho.mohsin.NotificationService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesho.mohsin.NotificationService.constants.KafkaConstants;
import com.meesho.mohsin.NotificationService.consumers.KafkaConsumerService;
import com.meesho.mohsin.NotificationService.dto.SmsDto;
import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.request.MessageRequestBody;
import com.meesho.mohsin.NotificationService.model.response.BlackListResponse;
import com.meesho.mohsin.NotificationService.model.response.SuccessMessageResponse;
import com.meesho.mohsin.NotificationService.producers.KafkaProducerService;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository2;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import com.meesho.mohsin.NotificationService.repository.cache.SmsCacheRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

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

        //restHighLevelClient.search();

        return smsDtos;
    }

    public SuccessMessageResponse sendMessage(MessageRequestBody messageRequestBody) throws JsonProcessingException {

        log.debug("inside sendMessage !");
        SmsRequests sms = new SmsRequests();
        sms.setPhone_number(messageRequestBody.getPhone_number());
        sms.setMessage(messageRequestBody.getMessage());
        sms.setStatus("OK");
        log.debug("before smsRepo");
        SmsRequests smsRequestSaved = smsRepository.saveAndFlush(sms);
        log.debug("after smsRepo");
        smsCacheRepository.refreshCache(Collections.singletonList(smsRequestSaved.getId()));
        log.debug("after refreshCache");

        System.out.println("Before producer");
        kafkaProducerService.sendMsg(KafkaConstants.SMS_CONSUMER_TOPIC,smsRequestSaved.getId());
        System.out.println("After producer");

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
        log.info("inside deleteFromBlackList");
        blackListedRepository.delete(phone_number);
        log.info("after blackListRepo");
        blackListedRepository2.deleteById(phone_number);
        log.info("after blackListRepo2");
        SuccessMessageResponse successMessageResponse = new SuccessMessageResponse();
        successMessageResponse.setMessage("successfully deleted "+phone_number+" from blacklist");
        return successMessageResponse;
    }

    public BlackListResponse getBlackList(){

        log.info("inside getBlackList() SmsService");
        Set<String> blackListResponseFromCache = blackListedRepository.findAll();

        if(blackListResponseFromCache.size() == 0){
            //fetch from database and put in cache
            log.info("cache is empty so fetching from db and inserting into cache");

            List<BlackListedEntity> blackListedEntities = blackListedRepository2.findAll();
            List<String> phoneNumbers = new ArrayList<>();
            for(BlackListedEntity blackListedEntity : blackListedEntities) {
                phoneNumbers.add(blackListedEntity.getPhone_number());
                blackListResponseFromCache.add(blackListedEntity.getPhone_number());
            }
            addToBlackList(phoneNumbers);
        }

        BlackListResponse blackListResponse = new BlackListResponse();
        blackListResponse.setData(blackListResponseFromCache);
        return blackListResponse;

    }

}
