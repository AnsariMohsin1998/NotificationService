package com.meesho.mohsin.NotificationService.repository.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meesho.mohsin.NotificationService.model.SmsRequests;
import com.meesho.mohsin.NotificationService.repository.SmsRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Component
public class SmsCacheRepository {

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SmsRepository smsRepository;

    public List<SmsRequests> findAll(){
        return smsRepository.findAll();
    }

    public SmsRequests findById(Integer id) throws JsonProcessingException {
        String response = redisTemplate.opsForValue().get(getKey(id));

        SmsRequests smsRequest = null;

        if (!StringUtils.isEmpty(response)) {
            smsRequest = objectMapper.readValue(response, SmsRequests.class);
        } else {

            SmsRequests smsRequestFromDb = smsRepository.findById(id).get();

            if (smsRequestFromDb != null) {
                redisTemplate.opsForValue().set(getKey(id), objectMapper.writeValueAsString(smsRequestFromDb));
            }

            System.out.println("FETCHING FROM DB");
            return smsRequestFromDb;

        }

        // to check if a user is blacklisted
        //redisTemplate.opsForSet().isMember("");

        // to add blacklisted users
        //redisTemplate.opsForSet().add("",);

        // to remove blacklisted user
        //redisTemplate.opsForSet().remove("");

        return smsRequest;
    }

    public void refreshCache(List<Integer> ids) throws JsonProcessingException {
        List<SmsRequests> smsRequests = smsRepository.findAllById(ids);

        for(SmsRequests smsRequest : smsRequests){
            redisTemplate.opsForValue().set(getKey(smsRequest.getId()),objectMapper.writeValueAsString(smsRequest));
        }
    }

    public String getKey(Integer id){
        return "SMS_REQUEST_"+id;
    }
}
