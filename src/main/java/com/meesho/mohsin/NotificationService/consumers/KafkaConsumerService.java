package com.meesho.mohsin.NotificationService.consumers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesho.mohsin.NotificationService.constants.KafkaConstants;
import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.model.elasticsearchmodel.ElasticSearchBody;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository;
import com.meesho.mohsin.NotificationService.repository.BlackListedRepository2;
import com.meesho.mohsin.NotificationService.repository.SearchRepository;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import com.meesho.mohsin.NotificationService.service.ImiConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@EnableConfigurationProperties
public class KafkaConsumerService {
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

    @Autowired
    SearchRepository searchRepository;

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

//                Saving in ES
//                ElasticSearchBody elasticSearchSavedData = ElasticSearchBody.builder().id(String.valueOf(smsRequest.getId())).message(smsRequest.getMessage()).phone_number(smsRequest.getPhone_number()).created_at(smsRequest.getCreated_at()).build();
//                elasticsearchOperations.save(elasticSearchSavedData);

                // Saving in ES 2
                ElasticSearchBody elasticSearchBody = ElasticSearchBody.builder().id(String.valueOf(smsRequest.getId())).phone_number(smsRequest.getPhone_number()).message(smsRequest.getMessage()).createdAt(smsRequest.getCreated_at()).build();
                searchRepository.save(elasticSearchBody);

                log.info("Saved in elastic search");

                // send to 3rd party api
                //String responseFromImiConnect = imiConnectService.send(smsRequest);

                log.info("message send to 3rd party api , but not used because it is costly");
                //log.info("response received from ImiConnect is : "+responseFromImiConnect);

            }
        }
    }
}
