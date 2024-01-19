package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.categories.CategoryResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.CategoryProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long, CategoryCreateRequest, CategoryUpdateRequest, CategoryResponse> {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public CategoryProductProjection getProductsByCategoryId(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }

        return repository.findProductsById(id, CategoryProductProjection.class);
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
