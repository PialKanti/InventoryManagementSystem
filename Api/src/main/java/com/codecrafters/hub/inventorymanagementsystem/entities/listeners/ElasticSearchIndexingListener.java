package com.codecrafters.hub.inventorymanagementsystem.entities.listeners;

import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import jakarta.persistence.PostPersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

@RequiredArgsConstructor
@Slf4j
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

        try {
            jmsTemplate.convertAndSend("productElasticSearch", entityToBeIndexed);
        } catch (JmsException e) {
            log.error("Error thrown while sending message to Jms message queue. Message: {}", e.getMessage());
        }
    }
}
