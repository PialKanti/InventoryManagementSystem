package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.products.ProductUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.products.ProductResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import com.codecrafters.hub.inventorymanagementsystem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, Long, ProductCreateRequest, ProductUpdateRequest, ProductResponse> {

    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
    }

    @Override
    protected Product convertToCreateEntity(ProductCreateRequest request) {
        return Product
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    protected Product convertToUpdateEntity(Product entity, ProductUpdateRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuality());

        return entity;
    }

    @Override
    protected ProductResponse convertToEntityResponse(Product entity) {
        return ProductResponse
                .builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
