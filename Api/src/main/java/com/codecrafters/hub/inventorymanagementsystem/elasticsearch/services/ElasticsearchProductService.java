package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services;

import co.elastic.clients.elasticsearch.core.*;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.constants.Indexes;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.repositories.ElasticsearchProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ElasticsearchProductService {
    private final ElasticsearchProductRepository repository;

    public IndexResponse create(Product product) {
        try {
            IndexRequest<Product> indexRequest = IndexRequest.of(builder -> builder
                    .index(Indexes.INDEX_PRODUCT)
                    .id(String.valueOf(product.getId()))
                    .document(product));
            return repository.add(indexRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UpdateResponse<Product> update(Product product) {
        try {
            UpdateRequest<Product, Product> updateRequest = UpdateRequest.of(builder -> builder
                    .index(Indexes.INDEX_PRODUCT)
                    .id(String.valueOf(product.getId()))
                    .doc(product));
            return repository.update(updateRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DeleteResponse delete(Product product) {
        try {
            DeleteRequest deleteRequest = DeleteRequest.of(builder -> builder
                    .index(Indexes.INDEX_PRODUCT)
                    .id(String.valueOf(product.getId())));
            return repository.delete(deleteRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
