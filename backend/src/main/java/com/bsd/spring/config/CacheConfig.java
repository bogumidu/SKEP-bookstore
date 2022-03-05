package com.bsd.spring.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // Configure frequently used caches
        CaffeineCache userCache = buildCache("plans", 24);
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(userCache));
        return manager;
    }

    private CaffeineCache buildCache(String name, int hoursToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(hoursToExpire, TimeUnit.HOURS)
                .maximumSize(100)
                .build());
    }

}
