package com.example.Weather.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();

        manager.setCaches(List.of(
                new CaffeineCache("geocoding",
                        Caffeine.newBuilder()
                                .expireAfterWrite(10, TimeUnit.HOURS)
                                .maximumSize(1000)
                                .build()
                ),
                new CaffeineCache("currentWeather",
                        Caffeine.newBuilder()
                                .expireAfterWrite(10, TimeUnit.MINUTES)
                                .maximumSize(1000)
                                .build()
                ),
                new CaffeineCache("forecast",
                        Caffeine.newBuilder()
                                .expireAfterWrite(1, TimeUnit.HOURS)
                                .maximumSize(500)
                                .build()
                )
        ));

        return manager;
    }
}






