package com.codecrafters.hub.inventorymanagementsystem.service.rabbitmq.consumer;

import com.codecrafters.hub.inventorymanagementsystem.model.entity.elasticsearch.ProductDocument;
import com.codecrafters.hub.inventorymanagementsystem.service.elasticsearch.ElasticsearchProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.codecrafters.hub.inventorymanagementsystem.constant.RabbitQueueName.PRODUCT_DELETE_QUEUE;

@RabbitListener(queues = PRODUCT_DELETE_QUEUE)
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDeleteListener {
    private final ElasticsearchProductService elasticsearchProductService;

    @RabbitHandler
    public void receive(ProductDocument product){
        log.info("Received product for deleting document in elastic search: {}", product);
        var response = elasticsearchProductService.delete(product);
        log.info("Elastic search product delete result: {}", response.result());
    }
}