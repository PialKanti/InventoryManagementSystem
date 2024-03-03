package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts.CartUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Cart;
import com.codecrafters.hub.inventorymanagementsystem.entities.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CartRepository;
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
