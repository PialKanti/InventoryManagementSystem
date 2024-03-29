package com.codecrafters.hub.inventorymanagementsystem.services.tokens;

import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.configs.JwtConfig;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import com.codecrafters.hub.inventorymanagementsystem.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @Mock
    private BlackListedTokenRepository repository;
    private JwtService testService;
    private JwtConfig jwtConfig;

    @BeforeEach
    void setUp() {
        jwtConfig = new JwtConfig();
        jwtConfig.setEncryptionKey("5heUCJxsTsaYyTE6+XB+tnpvdZkttNv5PBWHe9v9q73kGjDa2+UI2YQMWPj1Fpec");
        jwtConfig.setTokenExpiration(600000);

        testService = new JwtService(jwtConfig, repository);
    }

    @Test
    void checkExtractUsername() {
        //given
        String actual = "Robert";

        User user = User
                .builder()
                .username(actual)
                .build();

        String token = testService.generateToken(user);

        //when
        String expected = testService.extractUsername(token);

        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void checkIfTokenRevoked() {
        //given
        given(repository.findByToken(anyString()))
                .willReturn(Optional.of(BlackListedToken.builder().build()));
        //when
        boolean expected = testService.isTokenRevoked(anyString());

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void checkIfTokenNotRevoked() {
        //given
        given(repository.findByToken(anyString()))
                .willReturn(Optional.empty());
        //when
        boolean expected = testService.isTokenRevoked(anyString());

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void checkIfTokenExpired() {
        // Given
        long accessTokenExpiration = jwtConfig.getTokenExpiration();
        ReflectionTestUtils.setField(jwtConfig, "tokenExpiration", 1L);

        User user = User.builder().username("Robert").build();
        String expiredToken = testService.generateToken(user);

        // When

        // Then
        assertThatThrownBy(() -> testService.isTokenExpired(expiredToken))
                .isInstanceOf(ExpiredJwtException.class);

        ReflectionTestUtils.setField(jwtConfig, "tokenExpiration", accessTokenExpiration);
    }

    @Test
    void checkIfTokenNotExpired() {
        // Given
        User user = User.builder().username("Robert").build();
        String expiredToken = testService.generateToken(user);

        // When
        boolean expected = testService.isTokenExpired(expiredToken);

        // Then
        assertThat(expected).isFalse();
    }

    @Test
    void checkIfTokenValid() {
        // Given
        User user = User.builder().username("Robert").build();

        String token = testService.generateToken(user);

        // When
        boolean expected = testService.isTokenValid(token, user);

        // Then
        assertThat(expected).isTrue();
    }

    @Test
    void checkIfTokenNotValid() {
        // Given
        User originalUser = User.builder().username("Robert").build();
        User testUser = User.builder().username("Bruce").build();

        String token = testService.generateToken(originalUser);

        // When
        boolean expected = testService.isTokenValid(token, testUser);

        // Then
        assertThat(expected).isFalse();
    }
}