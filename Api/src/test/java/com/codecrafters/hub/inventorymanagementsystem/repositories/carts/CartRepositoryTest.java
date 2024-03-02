package com.codecrafters.hub.inventorymanagementsystem.repositories.carts;

import com.codecrafters.hub.inventorymanagementsystem.entities.Cart;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CartRepositoryTest {
    @Autowired
    private CartRepository repository;
    private Cart cart;

    @BeforeEach
    void setupTestData(){
        cart = Cart
                .builder()
                .username("robert")
                .cartItems(new ArrayList<>())
                .build();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testIfExistsByUsername(){
        //given
        repository.save(cart);

        //when
        var isExists = repository.existsByUsername("robert");

        //test
        assertThat(isExists).isTrue();
    }

    @Test
    void testIfNotExistsByUsername(){
        //given
        repository.save(cart);

        //when
        var isExists = repository.existsByUsername("bruce");

        //then
        assertThat(isExists).isFalse();
    }
}
