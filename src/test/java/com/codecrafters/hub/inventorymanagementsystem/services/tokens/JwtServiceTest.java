package com.codecrafters.hub.inventorymanagementsystem.services.tokens;

import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.properties.JwtProperties;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import com.codecrafters.hub.inventorymanagementsystem.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = JwtProperties.class)
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @Mock
    private BlackListedTokenRepository repository;
    private JwtService testService;
    @Autowired
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        testService = new JwtService(jwtProperties, repository);
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
    void checkIfTokenNotExpired() {
        // Given
        User user = User.builder().username("Robert").build();
        String expiredToken = testService.generateToken(user);

        // When
        boolean expected = testService.isTokenExpired(expiredToken);

        // Then
        assertThat(expected).isFalse();
    }
}