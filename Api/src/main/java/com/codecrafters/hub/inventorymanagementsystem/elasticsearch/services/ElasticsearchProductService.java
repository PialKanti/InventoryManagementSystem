package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services;

import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
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
}
