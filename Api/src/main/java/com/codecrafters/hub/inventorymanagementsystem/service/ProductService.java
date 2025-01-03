package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductRatingRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.EntityResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.products.ProductResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.products.RatingResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Category;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Rating;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.User;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import com.codecrafters.hub.inventorymanagementsystem.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseCrudService<Product, Long> {
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          UserService userService,
                          ObjectMapper objectMapper) {
        super(productRepository, objectMapper);
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public ProductResponse create(ProductCreateRequest request) {
        Product product = mapToEntity(request);
        return mapToDto(save(product), ProductResponse.class);
    }

    public List<EntityResponse> createInBulk(List<ProductCreateRequest> bulkRequest) {
        List<Product> products = bulkRequest.stream().map(this::mapToEntity).toList();
        var bulkResponse = saveAll(products);
        return bulkResponse.stream().map(entity -> mapToDto(entity, ProductResponse.class))
                .collect(Collectors.toList());
    }

    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = findById(id, Product.class);
        product.setTitle(request.title());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        return mapToDto(save(product), ProductResponse.class);
    }

    public RatingResponse addRating(Long productId, ProductRatingRequest request) {
        Product product = findById(productId, Product.class);
        User user = userService.findByUsername(request.username(), User.class);

        Rating rating = Rating.builder()
                .rating(request.rating())
                .comment(request.comment())
                .product(product)
                .user(user)
                .createdOn(LocalDateTime.now())
                .build();

        var ratings = Optional.ofNullable(product.getRatings()).orElseGet(ArrayList::new);
        ratings.add(rating);
        product.setRatings(ratings);

        double averageRating = Optional.ofNullable(product.getRatings())
                .orElseGet(Collections::emptyList)
                .stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0.0);
        product.setAverageRating((float) averageRating);

        save(product);

        return RatingResponse.builder()
                .ratings(rating.getRating())
                .comment(request.comment())
                .user(UserResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName()).build())
                .build();
    }

    public boolean isStockAvailable(Long productId, int quantity) {
        Product product = findById(productId, Product.class);
        return product.getQuantity() >= quantity;
    }

    public void updateStock(Product product, int quantity) {
        product.setQuantity(quantity);
        super.save(product);
    }

    private Product mapToEntity(ProductCreateRequest request) {
        return Product
                .builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .category(categoryService.findById(request.categoryId(), Category.class))
                .build();
    }

    @Override
    protected String getEntityNotFoundMessage() {
        return ExceptionConstant.PRODUCT_NOT_FOUND.getMessage();
    }
}
