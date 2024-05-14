package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductRatingRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.products.RatingResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Rating;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<BasePaginatedResponse<ProductProjection>> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                                            @RequestParam(name = "sort_by", defaultValue = "id", required = false) String sortBy,
                                                                            @RequestParam(name = "order_by", defaultValue = "asc", required = false) String orderBy) {
        Sort sortable = ("asc".equals(orderBy)) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sortable);
        return ResponseEntity.ok(service.findAll(pageable, ProductProjection.class));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductProjection> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id, ProductProjection.class));
    }

    @PostMapping
    public ResponseEntity<EntityResponse> create(@RequestBody ProductCreateRequest request) {
        var entityResponse = service.create(request);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityResponse.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(uriString)).body(entityResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EntityResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/ratings")
    public ResponseEntity<RatingResponse> addRating(@PathVariable(name = "id") Long productId, @RequestBody ProductRatingRequest request) {
        return ResponseEntity.ok(service.addRating(productId, request));
    }
}
