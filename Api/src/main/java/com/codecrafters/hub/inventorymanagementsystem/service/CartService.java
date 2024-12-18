package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.exception.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class CartService extends BaseCrudService<Cart, Long> {
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
        if (cartRepository.existsByUsername(request.username())) {
            throw new DuplicateCartException(ExceptionConstant.DUPLICATE_CART_EXCEPTION.getMessage());
        }

        Cart cart = Cart
                .builder()
                .username(request.username())
                .cartItems(request.cartItems().stream()
                        .map(this::getCartItemEntity)
                        .toList())
                .build();

        return mapToDto(save(cart), CartResponse.class);
    }

    private CartItem getCartItemEntity(CartItemDto cartItemDto) {
        var product = productService.findById(cartItemDto.productId(), Product.class);
        return CartItem
                .builder()
                .product(product)
                .quantity(cartItemDto.quantity())
                .build();
    }

    @Override
    protected String getEntityNotFoundMessage() {
        return ExceptionConstant.CART_NOT_FOUND.getMessage();
    }
}
