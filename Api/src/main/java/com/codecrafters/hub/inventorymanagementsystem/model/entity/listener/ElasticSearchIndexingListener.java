package com.codecrafters.hub.inventorymanagementsystem.model.entity.listener;

import com.codecrafters.hub.inventorymanagementsystem.constant.RabbitQueueName;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.ProductDocument;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import com.codecrafters.hub.inventorymanagementsystem.service.rabbitmq.producer.RabbitMQProducer;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ElasticSearchIndexingListener {
    private final RabbitMQProducer rabbitMQProducer;

    @PostPersist
    private void trackPostPersist(Product product) {
        sendToMessageQueue(product, RabbitQueueName.PRODUCT_CREATE_QUEUE);
    }

    @PostUpdate
    private void trackPostUpdate(Product product) {
        sendToMessageQueue(product, RabbitQueueName.PRODUCT_UPDATE_QUEUE);
    }

    @PostRemove
    private void trackPostRemove(Product product) {
        sendToMessageQueue(product, RabbitQueueName.PRODUCT_DELETE_QUEUE);
    }

    private void sendToMessageQueue(Product product, String destination) {
        var entityToBeIndexed = convertToDocumentEntity(product);
        rabbitMQProducer.send(destination, entityToBeIndexed);
    }

    private ProductDocument convertToDocumentEntity(Product product) {
        return ProductDocument.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
