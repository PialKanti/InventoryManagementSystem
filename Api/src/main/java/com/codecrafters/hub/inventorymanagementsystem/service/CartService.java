package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.exception.InsufficientProductException;
import com.codecrafters.hub.inventorymanagementsystem.exception.UnauthenticatedUserException;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts.CartItemUpsertRequest;
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
    public CartResponse addItemToCart(CartItemUpsertRequest createRequest) {
        Cart cart = getCurrentUserCart(true);

        CartItem cartItem = getMatchingCartItem(cart,
                item -> item.getProduct().getId().equals(createRequest.productId())).orElse(null);

        return addOrUpdateItemToCart(cart, cartItem, createRequest);
    }

    @Transactional
    public CartResponse updateItemInCart(Long itemId, CartItemUpsertRequest updateRequest) {
        Cart cart = getCurrentUserCart(false);

        CartItem cartItem = getMatchingCartItem(cart, item -> item.getId().equals(itemId))
                .orElseThrow(() -> super.entityNotFoundException(ExceptionConstant.CART_ITEM_NOT_FOUND.getMessage()));


        return addOrUpdateItemToCart(cart, cartItem, updateRequest);
    }

    private Cart getCurrentUserCart(boolean createIfNotExist) {
        UserDetails currentUser = getAuthenticatedUser();
        return cartRepository.findByUsernameAndDeletedFalse(currentUser.getUsername(), Cart.class)
                .orElseGet(() -> {
                    if (createIfNotExist) {
                        return create(currentUser.getUsername());
                    } else {
                        throw super.entityNotFoundException();
                    }
                });
    }


    private Cart create(String username){
        Cart cart = Cart.builder()
                .username(username)
                .build();

        return cartRepository.save(cart);
    }

    private CartResponse addOrUpdateItemToCart(Cart cart, CartItem cartItem, CartItemUpsertRequest request) {
        int quantityToAdjust = request.quantity();

        if (cartItem != null) {
            quantityToAdjust -= cartItem.getQuantity();

            validateStockAvailability(request.productId(), quantityToAdjust);

            cartItem.setQuantity(request.quantity());
        } else {
            validateStockAvailability(request.productId(), quantityToAdjust);

            cartItem = CartItem.builder()
                    .product(productService.findById(request.productId(), Product.class))
                    .quantity(request.quantity())
                    .build();

            cart.addCartItem(cartItem);
        }

        Cart savedCart = cartRepository.save(cart);

        Product cartItemProduct = cartItem.getProduct();
        productService.updateStock(cartItemProduct, cartItemProduct.getQuantity() - quantityToAdjust);

        return objectMapper.convertValue(savedCart, CartResponse.class);
    }

    @Override
    protected String getEntityNotFoundMessage() {
        return ExceptionConstant.CART_NOT_FOUND.getMessage();
    }

    private void validateStockAvailability(Long productId, int quantity) {
        if (quantity > 0 && !productService.isStockAvailable(productId, quantity)) {
            throw insufficientProductException();
        }
    }

    private UserDetails getAuthenticatedUser() {
        return SecurityUtil.getCurrentUser()
                .orElseThrow(this::unauthenticatedUserException);
    }

    private Optional<CartItem> getMatchingCartItem(Cart cart, Predicate<CartItem> predicate) {
        return cart.getCartItems().stream()
                .filter(predicate)
                .findFirst();
    }

    private UnauthenticatedUserException unauthenticatedUserException() {
        return new UnauthenticatedUserException(ExceptionConstant.UNAUTHENTICATED_USER_EXCEPTION.getMessage());
    }

    private InsufficientProductException insufficientProductException() {
        return new InsufficientProductException(ExceptionConstant.INSUFFICIENT_PRODUCTS_EXCEPTION.getMessage());
    }
}
