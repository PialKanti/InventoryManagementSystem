package com.codecrafters.hub.inventorymanagementsystem.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RedisHashKey {
    public static final String BLACKLISTED_TOKEN_CACHE_KEY_PREFIX = "auth:blacklistedTokens";
    public static final String USER_CACHE_KEY_PREFIX = "users:";
    public static final String CATEGORY_CACHE_KEY_PREFIX = "categories:";
}
