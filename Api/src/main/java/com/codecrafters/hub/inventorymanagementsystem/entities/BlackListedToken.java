package com.codecrafters.hub.inventorymanagementsystem.entities;

import com.codecrafters.hub.inventorymanagementsystem.constans.RedisHashKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@RedisHash(value = RedisHashKey.AUTH_BLACKLISTED_TOKEN, timeToLive = 600)
public class BlackListedToken {
    @Id
    private Long id;
    @Indexed
    private String token;
}
