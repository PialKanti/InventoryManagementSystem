package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services;

import co.elastic.clients.elasticsearch.core.*;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.constants.Indexes;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.dtos.request.ProductSearchRequest;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.ProductDocument;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.repositories.ElasticsearchProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ElasticsearchProductService {
    private final ElasticsearchProductRepository repository;

    public IndexResponse create(ProductDocument product) {
        try {
            IndexRequest<ProductDocument> indexRequest = IndexRequest.of(builder -> builder
                    .index(Indexes.INDEX_PRODUCT)
                    .id(String.valueOf(product.getId()))
                    .document(product));
            return repository.add(indexRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UpdateResponse<ProductDocument> update(ProductDocument product) {
        try {
            UpdateRequest<ProductDocument, ProductDocument> updateRequest = UpdateRequest.of(builder -> builder
                    .index(Indexes.INDEX_PRODUCT)
                    .id(String.valueOf(product.getId()))
                    .doc(product));
            return repository.update(updateRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DeleteResponse delete(ProductDocument product) {
        try {
            DeleteRequest deleteRequest = DeleteRequest.of(builder -> builder
                    .index(Indexes.INDEX_PRODUCT)
                    .id(String.valueOf(product.getId())));
            return repository.delete(deleteRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BasePaginatedResponse<ProductDocument> search(ProductSearchRequest searchCriteria, Pageable pageable) {
        return repository.search(searchCriteria, pageable);
    }
}
