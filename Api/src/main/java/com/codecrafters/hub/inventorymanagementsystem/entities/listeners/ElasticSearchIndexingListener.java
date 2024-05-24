package com.codecrafters.hub.inventorymanagementsystem.entities.listeners;

import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services.ElasticsearchProductService;
import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import jakarta.persistence.PostPersist;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElasticSearchIndexingListener {
    private final ElasticsearchProductService elasticsearchProductService;
    @PostPersist
    private void trackPostPersist(Product product) {
        //todo need to delegate the elasticsearch indexing code to Message blocker
        var entityToBeIndexed = com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
        elasticsearchProductService.create(entityToBeIndexed);
    }
}
