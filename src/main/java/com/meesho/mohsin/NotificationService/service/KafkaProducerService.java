package com.meesho.mohsin.NotificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    public KafkaTemplate<String,String> kafkaTemplate;

    // TOPIC
    public static final String TOPIC = "notification.send_sms";

    public void sendMsg(String msg){
        this.kafkaTemplate.send(TOPIC,msg);
    }

}
