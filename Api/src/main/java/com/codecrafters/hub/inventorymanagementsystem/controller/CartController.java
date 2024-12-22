package com.codecrafters.hub.inventorymanagementsystem.controller;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.projection.CartProjection;
import com.codecrafters.hub.inventorymanagementsystem.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<CartProjection> findCart() {
        return ResponseEntity.ok(cartService.findCurrentUserCart());
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(@RequestBody CartItemDto request) {
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateItem(@PathVariable Long itemId, @RequestBody CartItemUpdateRequest request) {
        return ResponseEntity.ok(cartService.updateItemInCart(itemId, request));
    }
}