package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.categories.CategoryResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Category;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.projection.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
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

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           ObjectMapper objectMapper) {
        super(categoryRepository, objectMapper);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public BasePaginatedResponse<ProductProjection> getProductsByCategoryId(Long id, Pageable pageable) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionConstant.CATEGORY_NOT_FOUND.getMessage());
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
        
        return mapToDto(save(category), CategoryResponse.class);
    }

    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        Category category = findById(id, Category.class);
        category.setName(request.getName());

        return mapToDto(save(category), CategoryResponse.class);
    }
}
