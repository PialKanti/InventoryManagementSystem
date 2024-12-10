package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.categories.CategoryResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Category;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.projection.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.repository.CategoryRepository;
import com.codecrafters.hub.inventorymanagementsystem.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long> {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           ObjectMapper objectMapper) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public BasePaginatedResponse<ProductProjection> getProductsByCategoryId(Long id, Pageable pageable) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }

        var page = productRepository.findByCategoryId(id, pageable, ProductProjection.class);

        return BasePaginatedResponse
                .<ProductProjection>builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }

    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = Category
                .builder()
                .name(request.getName())
                .build();
        
        return mapToResponse(save(category));
    }

    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        Category category = findById(id, Category.class);
        category.setName(request.getName());

        return mapToResponse(save(category));
    }

    private CategoryResponse mapToResponse(Category category) {
        return objectMapper.convertValue(category, CategoryResponse.class);
    }
}
