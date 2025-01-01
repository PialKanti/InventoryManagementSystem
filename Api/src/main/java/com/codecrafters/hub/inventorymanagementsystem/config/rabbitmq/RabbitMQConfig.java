package com.codecrafters.hub.inventorymanagementsystem.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.codecrafters.hub.inventorymanagementsystem.constant.RabbitQueueName.*;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue productCreateQueue() {
        return new Queue(PRODUCT_CREATE_QUEUE);
    }

    @Bean
    public Queue productUpdateQueue() {
        return new Queue(PRODUCT_UPDATE_QUEUE);
    }

    @Bean
    public Queue productDeleteQueue() {
        return new Queue(PRODUCT_DELETE_QUEUE);
    }

    @Bean
    public SimpleMessageConverter simpleMessageConverter() {
        var simpleMessageConverter = new SimpleMessageConverter();
        simpleMessageConverter.setAllowedListPatterns(List.of("*"));

        return simpleMessageConverter;
    }
}