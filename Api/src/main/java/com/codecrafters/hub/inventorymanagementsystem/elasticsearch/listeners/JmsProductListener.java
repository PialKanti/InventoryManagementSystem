package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.listeners;

import com.codecrafters.hub.inventorymanagementsystem.constant.JmsDestination;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.ProductDocument;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services.ElasticsearchProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JmsProductListener {
    private final ElasticsearchProductService elasticsearchProductService;

    @JmsListener(destination = JmsDestination.ELASTICSEARCH_PRODUCT_CREATE, containerFactory = "jmsListenerContainerFactory")
    public void createDocument(ProductDocument product) {
        log.info("Received product for creating document elastic search: {}", product);
        var response = elasticsearchProductService.create(product);
        log.info("Elastic search product created result: {}", response.result());
    }

    @JmsListener(destination = JmsDestination.ELASTICSEARCH_PRODUCT_UPDATE, containerFactory = "jmsListenerContainerFactory")
    public void updateDocument(ProductDocument productDocument) {
        log.info("Received product for updating document in elastic search: {}", productDocument);
        var response = elasticsearchProductService.update(productDocument);
        log.info("Elastic search product update result: {}", response.result());
    }

    @JmsListener(destination = JmsDestination.ELASTICSEARCH_PRODUCT_DELETE, containerFactory = "jmsListenerContainerFactory")
    public void deleteDocument(ProductDocument productDocument) {
        log.info("Received product for deleting document in elastic search: {}", productDocument);
        var response = elasticsearchProductService.delete(productDocument);
        log.info("Elastic search product delete result: {}", response.result());
    }
}
