package com.codecrafters.hub.inventorymanagementsystem.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RedisHashKey {
    public static final String AUTH_BLACKLISTED_TOKEN = "auth.blacklistedToken";
    public static final String USER_CACHE_KEY_PREFIX = "users:";
    public static final String CATEGORY_CACHE_KEY_PREFIX = "categories:";
}
