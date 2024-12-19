package com.codecrafters.hub.inventorymanagementsystem.controller;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.codecrafters.hub.inventorymanagementsystem.constant.ApiEndpointConstant.CART_ENDPOINT;

@RestController
@RequestMapping(value = CART_ENDPOINT)
@RequiredArgsConstructor
@Tag(
        name = "Cart API",
        description = "Endpoints for managing user cart, including add, updates, and deletion of items."
)
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Void> addItem(@RequestBody CartItemDto request) {
        cartService.addItemToCart(request);
        return ResponseEntity.ok().build();
    }
}