package com.codecrafters.hub.inventorymanagementsystem.services.carts;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.Cart;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.CartProjection;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CartRepository;
import com.codecrafters.hub.inventorymanagementsystem.services.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

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
        long id = 1;
        Cart cart = Cart.builder().id(id).username("robert").cartItems(new ArrayList<>()).build();
        CartCreateRequest createRequest = CartCreateRequest
                .builder()
                .username("robert")
                .cartItems(new ArrayList<>())
                .build();
        when(repository.existsByUsername(createRequest.getUsername())).thenReturn(false);
        when(repository.save(any())).thenReturn(cart);

        //when
        service.create(createRequest);
        //then
        verify(repository).existsByUsername(createRequest.getUsername());
        verify(repository).save(any());
    }

    @Test
    public void testUpdate_WhenCartIdNotExists() {
        //given
        long id = 1;
        CartUpdateRequest request = CartUpdateRequest.builder().cartItems(new ArrayList<>()).build();
        when(repository.findById(id)).thenThrow(new EntityNotFoundException());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.update(id, request));

        //then
        assertThat(exception).isNotNull().isInstanceOf(EntityNotFoundException.class);

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testUpdate_WhenCartIdExists() {
        //given
        long id = 1;
        Cart cart = Cart.builder().id(id).username("robert").cartItems(new ArrayList<>()).build();
        CartUpdateRequest request = CartUpdateRequest.builder().cartItems(new ArrayList<>()).build();
        when(repository.findById(id)).thenReturn(Optional.of(cart));
        when(repository.save(any())).thenReturn(cart);

        //when
        service.update(id, request);

        //then
        verify(repository).findById(id);
        verify(repository).save(cart);
    }

    @Test
    public void testFindByUsername() {
        //give
        String username = "robert";
        CartProjection cartProjection = mock(CartProjection.class);
        when(cartProjection.getUsername()).thenReturn(username);
        when(repository.findByUsername(username, CartProjection.class)).thenReturn(Optional.of(cartProjection));

        //when
        var result = service.findByUsername(username);

        //then
        assertThat(result).isNotNull().isEqualTo(cartProjection);
        assertThat(result.getUsername()).isEqualTo(username);
    }

    @Test
    public void testFindByUsername_WhenNoResult() {
        //give
        String username = "robert";
        when(repository.findByUsername(username, CartProjection.class)).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(EntityNotFoundException.class, () -> service.findByUsername(username));

        //then
        assertThat(exception).isNotNull().isInstanceOf(EntityNotFoundException.class);
    }
}
