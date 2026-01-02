package com.nasa.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RateLimitService {

    private final RedisTemplate<String,Integer> redisTemplate;
   public boolean isAllowed(String key,int limit,int windowSeconds){

       Long count = redisTemplate.opsForValue().increment(key);

       // First request → start time window
       if(count==1){
           redisTemplate.expire(key,windowSeconds, TimeUnit.SECONDS);
       }

       return count<=limit;
   }

}
