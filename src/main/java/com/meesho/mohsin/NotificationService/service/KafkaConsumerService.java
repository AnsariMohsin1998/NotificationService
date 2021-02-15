package com.meesho.mohsin.NotificationService.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesho.mohsin.NotificationService.constants.KafkaConstants;
import com.meesho.mohsin.NotificationService.controller.SmsController;
import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.kafka.ProducerRecord;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository2;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@EnableConfigurationProperties
public class KafkaConsumerService { // CONTROLLER
    @Autowired
    SmsRepository smsRepository;

    @Autowired
    BlackListedRepository blackListedRepository;

    @Autowired
    ImiConnectService imiConnectService;

    @Autowired
    BlackListedRepository2 blackListedRepository2;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    // containerFactory = KafkaConstants.SMS_CONSUMER_FACTORY)
    // ConsumerRecord<String, ProducerRecord> consumerRecord
    // Acknowledgment acknowledgment
    @KafkaListener(topics = KafkaConstants.SMS_CONSUMER_TOPIC,groupId = KafkaConstants.SMS_CONSUMER_GROUP_ID)
    public void consume(String data) throws JsonProcessingException, JsonMappingException {


        //ProducerRecord producerRecord = objectMapper.readValue(consumerRecord.value().toString(), ProducerRecord.class);

        //String id = producerRecord.getData();

        String id = data;

        log.info("Inside consume");

        // find sms by request_id
        SmsRequests smsRequest = smsRepository.findById(Integer.parseInt(id));
        String phone_number = smsRequest.getPhone_number();

        log.info("found smsRequest by id");


        //first check in cache

        if(blackListedRepository.exists(phone_number)){
            log.info("inside if");
            // number is blacklisted and present in cache
            smsRequest.setFailure_comments("number is in blacklist");
            smsRepository.save(smsRequest);
            log.info("number is in blacklist (if)");
        }
        else{
            log.info("inside else");
            // check if present in db
            Optional<BlackListedEntity> blackListedEntity = blackListedRepository2.findById(phone_number);
            log.info("fetched optional BlackListedEntity");
            if(blackListedEntity.isPresent()){
                // present in db and not in cache
                //update cache
                log.info("inside else if");
                blackListedRepository.save(blackListedEntity.get().getPhone_number());
                smsRequest.setFailure_comments("number is in blacklist");
                smsRepository.save(smsRequest);
                log.info("number is in blacklist (else)");
            }
            else{
                log.info("inside else else");
                // not present in db and in cache
                // so we can consume
                smsRequest.setStatus("sending to 3rd party");
                smsRepository.save(smsRequest);

                // send to 3rd party api
                String responseFromImiConnect = imiConnectService.send(smsRequest);

                log.info("message send to 3rd party api");
                log.info("response received from ImiConnect is : "+responseFromImiConnect);

            }
        }
    }
}
