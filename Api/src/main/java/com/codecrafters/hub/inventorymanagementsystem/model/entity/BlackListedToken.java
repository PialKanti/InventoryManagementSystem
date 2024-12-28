package com.codecrafters.hub.inventorymanagementsystem.model.entity;

import com.codecrafters.hub.inventorymanagementsystem.constant.RedisHashKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@RedisHash(value = RedisHashKey.BLACKLISTED_TOKEN_CACHE_KEY_PREFIX, timeToLive = 600)
public class BlackListedToken {
    @Id
    private Long id;
    @Indexed
    private String token;
}
