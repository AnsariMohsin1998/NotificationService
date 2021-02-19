package com.meesho.mohsin.NotificationService.repository;


import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import com.meesho.mohsin.NotificationService.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BlackListedRepository {


    private static final Logger log = LoggerFactory.getLogger(BlackListedRepository.class);

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void save(String number){
        redisTemplate.opsForSet().add("data",number);
    }
    public Set<String> findAll(){
        log.info("inside findAll() blackListRepository");
        return redisTemplate.opsForSet().members("data");
    }
    public boolean exists(String number){
        return redisTemplate.opsForSet().isMember("data",number);
    }
    public long delete(String number){
        System.out.println("inside delete inside repo");
        return redisTemplate.opsForSet().remove("data",number);
    }
    public boolean deleteAll(){
        return redisTemplate.delete("data");
    }

}
