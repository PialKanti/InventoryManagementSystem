package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.exception.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class CartService extends BaseService<Cart, Long> {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository,
                       ProductService productService,
                       ObjectMapper objectMapper) {
        super(cartRepository, objectMapper);
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public CartResponse create(CartCreateRequest request) {
        if (cartRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateCartException("User has already created a cart.");
        }

        Cart cart = Cart
                .builder()
                .username(request.getUsername())
                .cartItems(request.getCartItems().stream()
                        .map(this::getCartItemEntity)
                        .toList())
                .build();

        return mapToDto(save(cart), CartResponse.class);
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
