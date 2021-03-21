package com.cplanet.toring.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SampleCacheService {

    private final static Logger logger = LoggerFactory.getLogger(SampleCacheService.class);

//    @Cacheable(value = "threeMinCache", key = "'cache_'.concat(#name)")
    @Cacheable(value = "threeMinCache", key = "#name")
    public String getCacheEmail(String name) {
        return fromData();
    }


    private String fromData() {
        logger.debug("Call from DATA");
        return "nicekkong@gmail.com";
    }

//    @CacheEvict(value = {"#value"}, key = "#key")
    @CacheEvict(value = "threeMinCache", key = "#key")
    public void cacheEvict(String value, String key) {
        logger.info("   Cache cleared : {} ==> {}", value, key);
    }
}
