package com.meesho.mohsin.NotificationService.repository;


import com.meesho.mohsin.NotificationService.model.BlackListedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BlackListedRepository {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void save(String number){
        redisTemplate.opsForSet().add("data",number);
    }
    public Set<String> findAll(){
        return redisTemplate.opsForSet().members("data");
    }
    public boolean exists(String number){
        return redisTemplate.opsForSet().isMember("data",number);
    }
    public long delete(String number){
        return redisTemplate.opsForSet().remove("data",number);
    }
    public boolean deleteAll(){
        return redisTemplate.delete("data");
    }

}
