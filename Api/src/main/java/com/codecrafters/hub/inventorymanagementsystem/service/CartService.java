package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.exception.InsufficientProductException;
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
    public CartResponse addItemToCart(CartItemDto createRequest) {
        UserDetails currentUser = getAuthenticatedUser();

        Cart cart = cartRepository.findByUsernameAndDeletedFalse(currentUser.getUsername(), Cart.class)
                .orElseGet(() -> create(currentUser.getUsername()));

        Optional<CartItem> cartItemOptional = getMatchingCartItem(cart,
                item -> item.getProduct().getId().equals(createRequest.productId()));

        Product cartItemProduct;
        int quantityToAdjust = createRequest.quantity();

        if (cartItemOptional.isPresent()) {
            CartItem existingCartItem = cartItemOptional.get();

            quantityToAdjust -= existingCartItem.getQuantity();

            validateStockAvailability(createRequest.productId(), quantityToAdjust);

            existingCartItem.setQuantity(createRequest.quantity());

            cartItemProduct = existingCartItem.getProduct();
        } else {
            validateStockAvailability(createRequest.productId(), quantityToAdjust);

            CartItem newCartItem = CartItem.builder()
                    .product(productService.findById(createRequest.productId(), Product.class))
                    .quantity(createRequest.quantity())
                    .build();

            cart.addCartItem(newCartItem);

            cartItemProduct = newCartItem.getProduct();
        }

        Cart savedCart = cartRepository.save(cart);

        productService.updateStock(cartItemProduct, cartItemProduct.getQuantity() - quantityToAdjust);

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


        int quantityToAdjust = updateRequest.quantity() - cartItem.getQuantity();
        validateStockAvailability(updateRequest.productId(), quantityToAdjust);


        cartItem.setQuantity(updateRequest.quantity());

        Cart updatedCart = cartRepository.save(cart);

        productService.updateStock(cartItem.getProduct(), cartItem.getProduct().getQuantity() - quantityToAdjust);

        return objectMapper.convertValue(updatedCart, CartResponse.class);
    }

    private Cart create(String username){
        Cart cart = Cart.builder()
                .username(username)
                .build();

        return cartRepository.save(cart);
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
