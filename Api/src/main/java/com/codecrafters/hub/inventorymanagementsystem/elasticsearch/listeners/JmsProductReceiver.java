package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.listeners;

import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services.ElasticsearchProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JmsProductReceiver {
    private final ElasticsearchProductService elasticsearchProductService;

    @JmsListener(destination = "productElasticSearch", containerFactory = "jmsListenerContainerFactory")
    public void receive(Product product) {
        log.info("Received product for elastic search indexing: {}", product);
        var response = elasticsearchProductService.create(product);
        log.info("Elastic search product created result: {}", response.result());
    }
}
