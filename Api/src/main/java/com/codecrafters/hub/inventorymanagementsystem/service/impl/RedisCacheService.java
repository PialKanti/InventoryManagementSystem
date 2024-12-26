package com.codecrafters.hub.inventorymanagementsystem.service.impl;

import com.codecrafters.hub.inventorymanagementsystem.service.CacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.codecrafters.hub.inventorymanagementsystem.util.StringUtils.isNullOrEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService implements CacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> Optional<T> get(String key, Class<T> valueType) throws JsonProcessingException {
        String cachedValue = getAsString(key);
        if(isNullOrEmpty(cachedValue)) {
            return Optional.empty();
        }

        return Optional.of(objectMapper.readValue(cachedValue, valueType));
    }

    @Override
    public <T> List<T> getMany(String key, Class<T> itemValueType) throws JsonProcessingException {
        String cachedValue = getAsString(key);
        if(isNullOrEmpty(cachedValue)) {
            return Collections.emptyList();
        }

        return objectMapper.readValue(cachedValue, objectMapper.getTypeFactory().constructCollectionType(List.class, itemValueType));
    }

    @Override
    public void putAsString(String key, Object value) {
        writeToCacheAsString(key, value, false, 0L, null);
    }

    @Override
    public void putAsStringWithExpiry(String key, Object value, long timeout, TimeUnit unit) {
        writeToCacheAsString(key, value, true, timeout, unit);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    private String getAsString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    private void writeToCacheAsString(String key, Object value, boolean withExpiry, Long timeout, TimeUnit unit) {
        try {
            String serializedString = objectMapper.writeValueAsString(value);
            if (withExpiry) {
                redisTemplate.opsForValue().set(key, serializedString, timeout, unit);
            } else {
                redisTemplate.opsForValue().set(key, serializedString);
            }
        } catch (JsonProcessingException e) {
            log.error("[RedisCacheService] Can not write to Redis for key {}. Message = {}", key, e.getMessage());
        }
    }
}