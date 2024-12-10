package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.projection.CartProjection;
import com.codecrafters.hub.inventorymanagementsystem.exception.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService extends BaseService<Cart, Long> {
    private final CartRepository repository;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public CartService(CartRepository repository,
                       ProductService productService,
                       ObjectMapper objectMapper) {
        super(repository);
        this.repository = repository;
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    public CartResponse create(CartCreateRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new DuplicateCartException("User has already created a cart.");
        }

        Cart cart = Cart
                .builder()
                .username(request.getUsername())
                .cartItems(request.getCartItems().stream()
                        .map(this::getCartItemEntity)
                        .toList())
                .build();

        var createdEntity = super.save(cart);
        return objectMapper.convertValue(createdEntity, CartResponse.class);
    }

    public CartProjection findByUsername(String username) {
        return repository.findByUsername(username, CartProjection.class).orElseThrow(EntityNotFoundException::new);
    }

    private CartItem getCartItemEntity(CartItemDto cartItemDto) {
        var product = productService.findById(cartItemDto.getProductId(), Product.class);
        return CartItem
                .builder()
                .product(product)
                .quantity(cartItemDto.getQuantity())
                .build();
    }
}
