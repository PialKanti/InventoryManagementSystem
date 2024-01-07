package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Optional<Product> product = service.findById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductCreateRequest request) {
        Product createdEntity = service.create(request);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEntity.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(uriString)).body(createdEntity);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        try {
            Product updatedEntity = service.update(id, request);
            return ResponseEntity.ok(updatedEntity);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
