package com.codecrafters.hub.inventorymanagementsystem.service.rabbitmq.consumer;

import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.ProductDocument;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.services.ElasticsearchProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.codecrafters.hub.inventorymanagementsystem.constant.RabbitQueueName.PRODUCT_UPDATE_QUEUE;

@RabbitListener(queues = PRODUCT_UPDATE_QUEUE)
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductUpdateListener {
    private final ElasticsearchProductService elasticsearchProductService;

    @RabbitHandler
    public void receive(ProductDocument product) {
        log.info("Received product for updating document in elastic search: {}", product);
        var response = elasticsearchProductService.update(product);
        log.info("Elastic search product update result: {}", response.result());
    }
}