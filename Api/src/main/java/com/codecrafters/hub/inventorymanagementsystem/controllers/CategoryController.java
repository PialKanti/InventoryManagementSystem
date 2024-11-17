package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.CategoryProjection;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Category API",
        description = "Endpoints for managing product categories, including CRUD operations and retrieving associated products."
)
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    @Operation(
            summary = "Retrieve all categories",
            description = "Fetches a paginated list of categories.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve (default is 0)"),
                    @Parameter(name = "pageSize", description = "Number of categories per page (default is 5)")
            }
    )
    public ResponseEntity<BasePaginatedResponse<CategoryProjection>> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                             @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(service.findAll(pageable, CategoryProjection.class));
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Retrieve category by ID",
            description = "Fetches details of a category by its ID.",
            parameters = @Parameter(name = "id", description = "ID of the category to retrieve", required = true)
    )
    public ResponseEntity<CategoryProjection> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id, CategoryProjection.class));
    }

    @PostMapping
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category with the provided details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the category to create",
                    required = true
            )
    )
    public ResponseEntity<EntityResponse> create(@RequestBody CategoryCreateRequest request) {
        var entityResponse = service.create(request);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityResponse.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(uriString)).body(entityResponse);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update category by ID",
            description = "Updates the details of an existing category by its ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated category details",
                    required = true
            ),
            parameters = @Parameter(name = "id", description = "ID of the category to update", required = true)
    )
    public ResponseEntity<EntityResponse> update(@PathVariable(name = "id") Long id, @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete category by ID",
            description = "Deletes a category identified by its ID.",
            parameters = @Parameter(name = "id", description = "ID of the category to delete", required = true)
    )
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/products")
    @Operation(
            summary = "Retrieve products by category ID",
            description = "Fetches a paginated list of products associated with the given category ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the category to retrieve products for", required = true),
                    @Parameter(name = "page", description = "Page number to retrieve (default is 0)"),
                    @Parameter(name = "pageSize", description = "Number of products per page (default is 5)")
            }
    )
    public ResponseEntity<BasePaginatedResponse<ProductProjection>> getProducts(@PathVariable(name = "id") Long categoryId,
                                                                                @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(service.getProductsByCategoryId(categoryId, pageable));
    }
}