package com.meesho.mohsin.NotificationService.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesho.mohsin.NotificationService.constants.KafkaConstants;
import com.meesho.mohsin.NotificationService.model.kafka.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService<T> {

    @Autowired
    public KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper; // IMP

    public void sendMsg(String topic,T data) throws JsonProcessingException {

//        ProducerRecord producerRecord = new ProducerRecord();
//        producerRecord.setData(data.toString());
//        String payload = objectMapper.writeValueAsString(producerRecord);
        String payload = data.toString();

        this.kafkaTemplate.send(topic,payload);
    }

}

// DAO Data access object

// Generics in java
// Methods implemented like Object Mappers