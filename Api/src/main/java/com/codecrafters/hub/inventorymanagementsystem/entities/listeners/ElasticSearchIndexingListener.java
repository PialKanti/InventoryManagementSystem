package com.codecrafters.hub.inventorymanagementsystem.entities.listeners;

import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import jakarta.persistence.PostPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

@RequiredArgsConstructor
public class ElasticSearchIndexingListener {
    private final JmsTemplate jmsTemplate;

    @PostPersist
    private void trackPostPersist(Product product) {
        var entityToBeIndexed = com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
        jmsTemplate.convertAndSend("productElasticSearch", entityToBeIndexed);
    }
}
