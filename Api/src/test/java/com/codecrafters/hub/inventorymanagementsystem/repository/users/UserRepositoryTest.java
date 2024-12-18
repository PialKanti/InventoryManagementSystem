package com.codecrafters.hub.inventorymanagementsystem.repository.users;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.User;
import com.codecrafters.hub.inventorymanagementsystem.model.projection.UserProjection;
import com.codecrafters.hub.inventorymanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository testRepository;
    private User user;

    @BeforeEach
    void setupTestData() {
        user = User
                .builder()
                .firstName("Pial Kanti")
                .lastName("Samadder")
                .username("PialKanti")
                .email("pial@test.com")
                .password("12345")
                .build();
    }

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void testFindByUsernameProjection() {
        // given
        testRepository.save(user);

        // when
        var optional = testRepository.findByUsername(user.getUsername(), UserProjection.class);

        // test
        assertThat(optional).isPresent();
        assertThat(optional.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void testDeleteByUsername(){
        // given
        testRepository.save(user);

        // when
        testRepository.deleteByUsername(user.getUsername());
        var optional = testRepository.findByUsername(user.getUsername(), User.class);

        // test
        assertThat(optional).isEmpty();
    }
}
