package com.codecrafters.hub.inventorymanagementsystem.services.carts;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CartRepository;
import com.codecrafters.hub.inventorymanagementsystem.services.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository repository;
    @InjectMocks
    private CartService service;

    @Test
    public void testCreate_WhenUserHasAlreadyCreatedCart() {
        //given
        CartCreateRequest createRequest = CartCreateRequest
                .builder()
                .username("robert")
                .cartItems(new ArrayList<>())
                .build();
        when(repository.existsByUsername(createRequest.getUsername())).thenReturn(true);

        //when
        DuplicateCartException exception = assertThrows(DuplicateCartException.class, () -> service.create(createRequest));

        //then
        assertThat(exception).isNotNull().isInstanceOf(DuplicateCartException.class);

        verify(repository).existsByUsername(createRequest.getUsername());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testCreate_WhenUserHasNotCreatedCart() {
        //given
        CartCreateRequest createRequest = CartCreateRequest
                .builder()
                .username("robert")
                .cartItems(new ArrayList<>())
                .build();
        when(repository.existsByUsername(createRequest.getUsername())).thenReturn(false);

        //when
        service.create(createRequest);
        //then
        verify(repository).existsByUsername(createRequest.getUsername());
        verify(repository).save(any());
    }
}
