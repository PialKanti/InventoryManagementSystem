package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.exception.UnauthenticatedUserException;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemDto;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts.CartResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Cart;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import com.codecrafters.hub.inventorymanagementsystem.model.projection.CartProjection;
import com.codecrafters.hub.inventorymanagementsystem.repository.CartRepository;
import com.codecrafters.hub.inventorymanagementsystem.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CartService extends BaseCrudService<Cart, Long> {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public CartService(CartRepository cartRepository,
                       ProductService productService,
                       ObjectMapper objectMapper) {
        super(cartRepository, objectMapper);
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    public CartProjection findCurrentUserCart() {
        UserDetails currentUser = SecurityUtil.getCurrentUser()
                .orElseThrow(this::unauthenticatedUserException);

        return cartRepository.findByUsernameAndDeletedFalse(currentUser.getUsername(), CartProjection.class)
                .orElseThrow(super::entityNotFoundException);
    }

    @Transactional
    public CartResponse addItemToCart(CartItemDto cartItemDto) {
        UserDetails currentUser = SecurityUtil.getCurrentUser()
                .orElseThrow(this::unauthenticatedUserException);

        Cart cart = cartRepository.findByUsernameAndDeletedFalse(currentUser.getUsername(), Cart.class)
                .orElseGet(() -> create(currentUser.getUsername()));

        Optional<CartItem> existingCartItem = getMatchingCartItem(cart,
                item -> item.getProduct().getId().equals(cartItemDto.productId()));

        if(existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.quantity());
        }else{
            CartItem newCartItem = CartItem.builder()
                    .product(productService.findById(cartItemDto.productId(), Product.class))
                    .quantity(cartItemDto.quantity())
                    .build();

            cart.addCartItem(newCartItem);
        }

        Cart savedCart = cartRepository.save(cart);

        return objectMapper.convertValue(savedCart, CartResponse.class);
    }

    @Transactional
    public CartResponse updateItemInCart(Long itemId, CartItemUpdateRequest updateRequest) {
        UserDetails currentUser = SecurityUtil.getCurrentUser()
                .orElseThrow(this::unauthenticatedUserException);

        Cart cart = cartRepository.findByUsernameAndDeletedFalse(currentUser.getUsername(), Cart.class)
                .orElseThrow(super::entityNotFoundException);

        CartItem cartItem = getMatchingCartItem(cart, item -> item.getId().equals(itemId))
                .orElseThrow(() -> super.entityNotFoundException(ExceptionConstant.CART_ITEM_NOT_FOUND.getMessage()));

        cartItem.setQuantity(updateRequest.quantity());

        Cart updatedCart = cartRepository.save(cart);

        return objectMapper.convertValue(updatedCart, CartResponse.class);
    }

    private Cart create(String username){
        Cart cart = Cart.builder()
                .username(username)
                .build();

        return cartRepository.save(cart);
    }

    private Optional<CartItem> getMatchingCartItem(Cart cart, Predicate<CartItem> predicate) {
        return cart.getCartItems().stream()
                .filter(predicate)
                .findFirst();
    }

    @Override
    protected String getEntityNotFoundMessage() {
        return ExceptionConstant.CART_NOT_FOUND.getMessage();
    }

    private UnauthenticatedUserException unauthenticatedUserException() {
        return new UnauthenticatedUserException(ExceptionConstant.UNAUTHENTICATED_USER_EXCEPTION.getMessage());
    }
}
