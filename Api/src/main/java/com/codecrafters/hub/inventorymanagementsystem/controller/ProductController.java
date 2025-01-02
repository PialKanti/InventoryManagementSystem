package com.codecrafters.hub.inventorymanagementsystem.controller;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductRatingRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.EntityResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.products.RatingResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.elasticsearch.ProductDocument;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductSearchRequest;
import com.codecrafters.hub.inventorymanagementsystem.service.elasticsearch.ElasticsearchProductService;
import com.codecrafters.hub.inventorymanagementsystem.model.projection.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.codecrafters.hub.inventorymanagementsystem.constant.ApiEndpointConstant.PRODUCT_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = PRODUCT_ENDPOINT)
@Tag(
        name = "Product API",
        description = "Endpoints for managing products, including retrieval, bulk operations, searching, and adding ratings."
)
public class ProductController {
    private final ProductService service;
    private final ElasticsearchProductService esService;

    @GetMapping
    @Operation(
            summary = "Retrieve all products",
            description = "Fetches a paginated list of products with optional sorting.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve (default is 0)"),
                    @Parameter(name = "pageSize", description = "Number of products per page (default is 5)"),
                    @Parameter(name = "sort_by", description = "Field to sort by (default is 'id')"),
                    @Parameter(name = "order_by", description = "Sort order: 'asc' or 'desc' (default is 'asc')")
            }
    )
    public ResponseEntity<BasePaginatedResponse<ProductProjection>> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                                            @RequestParam(name = "sort_by", defaultValue = "id", required = false) String sortBy,
                                                                            @RequestParam(name = "order_by", defaultValue = "asc", required = false) String orderBy) {
        Sort sortable = ("asc".equals(orderBy)) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sortable);
        return ResponseEntity.ok(service.findAll(pageable, ProductProjection.class));
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search for products",
            description = "Searches for products using specified criteria and pagination.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve (default is 0)"),
                    @Parameter(name = "pageSize", description = "Number of products per page (default is 5)")
            }
    )
    public ResponseEntity<BasePaginatedResponse<ProductDocument>> search(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                                         @ModelAttribute ProductSearchRequest request) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(esService.search(request, pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Retrieve product by ID",
            description = "Fetches details of a product by its ID.",
            parameters = @Parameter(name = "id", description = "ID of the product to retrieve", required = true)
    )
    public ResponseEntity<ProductProjection> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id, ProductProjection.class));
    }

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product and returns its details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the product to create",
                    required = true
            )
    )
    public ResponseEntity<EntityResponse> createInBulk(@RequestBody ProductCreateRequest request) {
        var entityResponse = service.create(request);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityResponse.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(uriString)).body(entityResponse);
    }

    @PostMapping(value = "/bulk")
    @Operation(
            summary = "Create products in bulk",
            description = "Creates multiple products in a single operation.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of products to create",
                    required = true
            )
    )
    public ResponseEntity<List<EntityResponse>> createInBulk(@RequestBody List<ProductCreateRequest> bulkRequest) {
        var response = service.createInBulk(bulkRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update a product",
            description = "Updates an existing product by its ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product details",
                    required = true
            ),
            parameters = @Parameter(name = "id", description = "ID of the product to update", required = true)
    )
    public ResponseEntity<EntityResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its ID.",
            parameters = @Parameter(name = "id", description = "ID of the product to delete", required = true)
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/ratings")
    @Operation(
            summary = "Add a rating to a product",
            description = "Adds a rating for a product by its ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the rating to add",
                    required = true
            ),
            parameters = @Parameter(name = "id", description = "ID of the product to rate", required = true)
    )
    public ResponseEntity<RatingResponse> addRating(@PathVariable(name = "id") Long productId, @RequestBody ProductRatingRequest request) {
        return ResponseEntity.ok(service.addRating(productId, request));
    }
}