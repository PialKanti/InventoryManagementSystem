package com.codecrafters.hub.inventorymanagementsystem.repositories.tokens;

import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
class BlackListedTokenRepositoryTest {
    @Autowired
    private BlackListedTokenRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void checkIfTokenExists() {
        //given
        String tokenValue = any();
        BlackListedToken token = BlackListedToken
                .builder()
                .id(1L)
                .token(tokenValue)
                .build();
        testRepository.save(token);

        //when
        var optional = testRepository.findByToken(tokenValue);

        //then
        assertThat(optional.isPresent()).isTrue();
    }

    @Test
    void checkIfTokenNotExists() {
        //given
        String tokenValue = any();

        //when
        var optional = testRepository.findByToken(tokenValue);

        //then
        assertThat(optional.isPresent()).isFalse();
    }
}