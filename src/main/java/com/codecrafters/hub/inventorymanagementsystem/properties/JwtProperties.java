package com.codecrafters.hub.inventorymanagementsystem.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtProperties {
    @Value("${jwt.encryption.key}")
    private String encryptionKey;
    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

}
