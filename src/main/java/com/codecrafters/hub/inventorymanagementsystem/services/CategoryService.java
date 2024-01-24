package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.categories.CategoryResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.CategoryProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long, CategoryCreateRequest, CategoryUpdateRequest, CategoryResponse> {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public BasePaginatedResponse<ProductProjection> getProductsByCategoryId(Long id, Pageable pageable) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }

        var page = repository.findProductsByCategoryId(id, pageable);

        return BasePaginatedResponse
                .<ProductProjection>builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalItems(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }

    @Override
    protected Category convertToCreateEntity(CategoryCreateRequest request) {
        return Category
                .builder()
                .name(request.getName())
                .build();
    }

    @Override
    protected Category convertToUpdateEntity(Category entity, CategoryUpdateRequest request) {
        entity.setName(request.getName());
        return entity;
    }

    @Override
    protected CategoryResponse convertToEntityResponse(Category entity) {
        return CategoryResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
