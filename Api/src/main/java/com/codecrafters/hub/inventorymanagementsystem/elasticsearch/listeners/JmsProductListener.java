package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.listeners;

import com.codecrafters.hub.inventorymanagementsystem.constans.JmsDestination;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product;
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
    public void createDocument(Product product) {
        log.info("Received product for creating document elastic search: {}", product);
        var response = elasticsearchProductService.create(product);
        log.info("Elastic search product created result: {}", response.result());
    }

    @JmsListener(destination = JmsDestination.ELASTICSEARCH_PRODUCT_UPDATE, containerFactory = "jmsListenerContainerFactory")
    public void updateDocument(Product product) {
        log.info("Received product for updating document in elastic search: {}", product);
        var response = elasticsearchProductService.update(product);
        log.info("Elastic search product update result: {}", response.result());
    }

    @JmsListener(destination = JmsDestination.ELASTICSEARCH_PRODUCT_DELETE, containerFactory = "jmsListenerContainerFactory")
    public void deleteDocument(Product product) {
        log.info("Received product for deleting document in elastic search: {}", product);
        var response = elasticsearchProductService.delete(product);
        log.info("Elastic search product delete result: {}", response.result());
    }
}
