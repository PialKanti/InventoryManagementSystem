package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.exception.UnauthenticatedUserException;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.exception.DuplicateCartException;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
import com.codecrafters.hub.inventorymanagementsystem.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Transactional
    public void addItemToCart(CartItemDto cartItemDto) {
        UserDetails currentUser = SecurityUtil.getCurrentUser()
                .orElseThrow(() -> new UnauthenticatedUserException(ExceptionConstant.UNAUTHENTICATED_USER_EXCEPTION.getMessage()));

        Cart cart = cartRepository.findByUsername(currentUser.getUsername(), Cart.class)
                .orElseGet(() -> create(currentUser.getUsername()));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemDto.productId()))
                .findFirst();

        if(existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.quantity());
        }else{
            CartItem newCartItem = CartItem.builder()
                    .product(productService.findById(cartItemDto.productId(), Product.class))
                    .quantity(cartItemDto.quantity())
                    .build();

            cart.getCartItems().add(newCartItem);
        }

        cartRepository.save(cart);
    }

    private Cart create(String username){
        Cart cart = Cart.builder()
                .username(username)
                .build();

        return cartRepository.save(cart);
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
