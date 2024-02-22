package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.properties.JwtProperties;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;
    private final BlackListedTokenRepository repository;

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenRevoked(token);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate != null && expirationDate.before(new Date());
    }

    public boolean isTokenRevoked(String token) {
        var optional = repository.findByToken(token);
        return optional.isPresent();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getEncryptionKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
