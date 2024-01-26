package com.codecrafters.hub.inventorymanagementsystem.services.tokens;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

        //test
        assertThat(expected).isEqualTo(actual);
    }
}