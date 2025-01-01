package com.codecrafters.hub.inventorymanagementsystem.service.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    public void send(String queueName, Object message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
