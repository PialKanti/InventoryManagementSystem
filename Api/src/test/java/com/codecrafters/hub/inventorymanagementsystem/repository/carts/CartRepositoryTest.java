package com.codecrafters.hub.inventorymanagementsystem.repository.carts;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.projection.CartProjection;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
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
    public void setupTestData() {
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
    public void testIfExistsByUsername() {
        //given
        repository.save(cart);

        //when
        var isExists = repository.existsByUsername("robert");

        //test
        assertThat(isExists).isTrue();
    }

    @Test
    public void testIfNotExistsByUsername() {
        //given
        repository.save(cart);

        //when
        var isExists = repository.existsByUsername("bruce");

        //then
        assertThat(isExists).isFalse();
    }

    @Test
    public void testFindByUsername() {
        //given
        repository.save(cart);

        //when
        var optional = repository.findByUsername(cart.getUsername(), CartProjection.class);

        //then
        assertThat(optional).isNotEmpty();
        optional.ifPresent(cartProjection -> {
            assertThat(cartProjection).isInstanceOf(CartProjection.class);
            assertThat(cartProjection.getUsername()).isEqualTo(cart.getUsername());
        });
    }

    @Test
    public void testFindByUsername_WhenNoResult() {
        //given
        String username = "bruce";
        repository.save(cart);

        //when
        var result = repository.findByUsername(username, CartProjection.class);

        //then
        assertThat(result).isEmpty();
    }
}
