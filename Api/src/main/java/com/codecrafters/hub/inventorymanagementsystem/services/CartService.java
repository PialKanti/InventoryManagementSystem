package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Cart;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService extends BaseService<Cart, Long, CartCreateRequest, CartUpdateRequest, CartResponse>{
    private final CartRepository repository;

    public CartService(CartRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public CartResponse create(CartCreateRequest cartCreateRequest) {
        if(repository.existsByUsername(cartCreateRequest.getUsername())){
            throw new DuplicateCartException("User has already created a cart.");
        }
        return super.create(cartCreateRequest);
    }

    @Override
    protected Cart convertToCreateEntity(CartCreateRequest cartCreateRequest) {
        return null;
    }

    @Override
    protected Cart convertToUpdateEntity(Cart entity, CartUpdateRequest cartUpdateRequest) {
        return null;
    }

    @Override
    protected CartResponse convertToEntityResponse(Cart entity) {
        return null;
    }
}
