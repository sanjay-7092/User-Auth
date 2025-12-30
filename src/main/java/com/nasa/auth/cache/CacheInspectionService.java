package com.nasa.auth.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheInspectionService {
    private final CacheManager cacheManager;

    CacheInspectionService(CacheManager cacheManager){
        this.cacheManager=cacheManager;
    }

    public String getCacheConstants(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache!=null){
            return Objects.requireNonNull(cache.getNativeCache()).toString();
        }
        return "No Cache Found";
    }
}
