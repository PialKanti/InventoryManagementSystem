package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductRatingRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.products.ProductResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import com.codecrafters.hub.inventorymanagementsystem.entities.Rating;
import com.codecrafters.hub.inventorymanagementsystem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ProductService extends BaseService<Product, Long, ProductCreateRequest, ProductUpdateRequest, ProductResponse> {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository repository, CategoryService categoryService) {
        super(repository);
        this.productRepository = repository;
        this.categoryService = categoryService;
    }

    public Rating addRating(Long productId, ProductRatingRequest request) {
        Product product = findById(productId, Product.class);
        Rating rating = Rating.builder()
                .rating(request.rating())
                .comment(request.comment())
                .product(product)
                .createdOn(LocalDateTime.now())
                .build();

        var existingRatings = product.getRatings();
        if (existingRatings == null) {
            existingRatings = new ArrayList<>();
        }
        existingRatings.add(rating);

        product.setRatings(existingRatings);
        productRepository.save(product);

        return rating;
    }

    @Override
    protected Product convertToCreateEntity(ProductCreateRequest request) {
        return Product
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(categoryService.findById(request.getCategoryId(), Category.class))
                .build();
    }

    @Override
    protected Product convertToUpdateEntity(Product entity, ProductUpdateRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuantity());

        return entity;
    }

    @Override
    protected ProductResponse convertToEntityResponse(Product entity) {
        return ProductResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
