package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import com.codecrafters.hub.inventorymanagementsystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long, CategoryCreateRequest, CategoryUpdateRequest> {
    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    protected Category convertToCreateEntity(CategoryCreateRequest request) {
        return Category
                .builder()
                .name(request.getName())
                .build();
    }

    @Override
    protected Category convertToUpdateEntity(CategoryUpdateRequest request) {
        return Category
                .builder()
                .name(request.getName())
                .build();
    }
}
