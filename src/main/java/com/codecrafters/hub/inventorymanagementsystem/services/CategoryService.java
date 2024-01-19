package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.categories.CategoryResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CategoryRepository;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.CategoryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<CategoryProjection> findById(Long id) {
        return repository.findById(id, CategoryProjection.class);
    }

    public List<CategoryProjection> findAll() {
        return repository.findAllBy(CategoryProjection.class);
    }

    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = Category
                .builder()
                .name(request.getName())
                .build();

        Category createdEntity = repository.save(category);
        return CategoryResponse
                .builder()
                .id(createdEntity.getId())
                .name(createdEntity.getName())
                .build();
    }
}
