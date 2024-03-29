package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.CategoryProjection;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/categories")
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<BasePaginatedResponse<CategoryProjection>> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                             @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(service.findAll(pageable, CategoryProjection.class));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryProjection> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id, CategoryProjection.class));
    }

    @PostMapping
    public ResponseEntity<EntityResponse> create(@RequestBody CategoryCreateRequest request) {
        var entityResponse = service.create(request);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityResponse.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(uriString)).body(entityResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EntityResponse> update(@PathVariable(name = "id") Long id, @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/products")
    public ResponseEntity<BasePaginatedResponse<ProductProjection>> getProducts(@PathVariable(name = "id") Long categoryId,
                                                                                @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(service.getProductsByCategoryId(categoryId, pageable));
    }
}
