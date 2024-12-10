package com.codecrafters.hub.inventorymanagementsystem.model.entity.listener;

import com.codecrafters.hub.inventorymanagementsystem.constant.JmsDestination;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Product;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
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
        sendToMessageQueue(product, JmsDestination.ELASTICSEARCH_PRODUCT_CREATE);
    }

    @PostUpdate
    private void trackPostUpdate(Product product) {
        sendToMessageQueue(product, JmsDestination.ELASTICSEARCH_PRODUCT_UPDATE);
    }

    @PostRemove
    private void trackPostRemove(Product product) {
        sendToMessageQueue(product, JmsDestination.ELASTICSEARCH_PRODUCT_DELETE);
    }

    private void sendToMessageQueue(Product product, String destination) {
        var entityToBeIndexed = convertToDocumentEntity(product);

        try {
            jmsTemplate.convertAndSend(destination, entityToBeIndexed);
        } catch (JmsException e) {
            log.error("Error thrown while sending message to Jms message queue[Destination = {}]. Message: {}", destination, e.getMessage());
        }
    }

    private com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product convertToDocumentEntity(Product product) {
        return com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
