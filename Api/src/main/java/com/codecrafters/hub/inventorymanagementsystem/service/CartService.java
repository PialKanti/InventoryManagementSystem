package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.projection.CartProjection;
import com.codecrafters.hub.inventorymanagementsystem.exception.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartService extends BaseService<Cart, Long, CartCreateRequest, CartUpdateRequest, CartResponse> {
    private final CartRepository repository;
    private final ProductService productService;

    public CartService(CartRepository repository, ProductService productService) {
        super(repository);
        this.repository = repository;
        this.productService = productService;
    }

    @Override
    public CartResponse create(CartCreateRequest cartCreateRequest) {
        if (repository.existsByUsername(cartCreateRequest.getUsername())) {
            throw new DuplicateCartException("User has already created a cart.");
        }
        return super.create(cartCreateRequest);
    }

    public CartProjection findByUsername(String username) {
        return repository.findByUsername(username, CartProjection.class).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    protected Cart convertToCreateEntity(CartCreateRequest request) {
        return Cart
                .builder()
                .username(request.getUsername())
                .cartItems(request.getCartItems().stream()
                        .map(this::getCartItemEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private CartItem getCartItemEntity(CartItemDto cartItemDto) {
        var product = productService.findById(cartItemDto.getProductId(), Product.class);
        return CartItem
                .builder()
                .product(product)
                .quantity(cartItemDto.getQuantity())
                .build();
    }

    @Override
    protected Cart convertToUpdateEntity(Cart entity, CartUpdateRequest request) {
        entity.setCartItems(request.getCartItems().stream()
                .map(this::getCartItemEntity)
                .collect(Collectors.toList()));
        return entity;
    }

    @Override
    protected CartResponse convertToEntityResponse(Cart entity) {
        return CartResponse
                .builder()
                .username(entity.getUsername())
                .cartItems(entity.getCartItems())
                .build();
    }
}
