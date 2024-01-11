package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, Long, ProductCreateRequest, ProductUpdateRequest> {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    protected Product convertToCreateEntity(ProductCreateRequest request) {
        return new Product(request.getTitle(), request.getDescription(), request.getPrice(), request.getQuality());
    }

    @Override
    protected Product convertToUpdateEntity(ProductUpdateRequest request) {
        Product product = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuality(request.getQuality());

        return product;
    }
}
