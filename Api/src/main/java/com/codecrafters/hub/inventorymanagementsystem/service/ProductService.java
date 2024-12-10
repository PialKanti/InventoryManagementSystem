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
public class ProductService extends BaseService<Product, Long> {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository repository,
                          CategoryService categoryService,
                          UserService userService,
                          ObjectMapper objectMapper) {
        super(repository);
        this.productRepository = repository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public ProductResponse create(ProductCreateRequest request) {
        Product product = mapToEntity(request);

        var createdEntity = super.save(product);
        return mapToResponse(createdEntity);
    }

    public List<EntityResponse> createInBulk(List<ProductCreateRequest> bulkRequest) {
        List<Product> products = bulkRequest.stream().map(this::mapToEntity).toList();
        var bulkResponse = productRepository.saveAll(products);
        return bulkResponse.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = findById(id, Product.class);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        var updatedEntity = super.save(product);
        return mapToResponse(updatedEntity);
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

        productRepository.save(product);

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

    private Product mapToEntity(ProductCreateRequest request) {
        return Product
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(categoryService.findById(request.getCategoryId(), Category.class))
                .build();
    }

    private ProductResponse mapToResponse(Product entity) {
        return objectMapper.convertValue(entity, ProductResponse.class);
    }
}
