package com.oscarkara.pubSub.service;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TopicSubscriptionService {

    @Autowired
    private AmqpAdmin amqpAdmin;

    public void createTopicExchange(UUID topicId) {
        String exchangeName = "exchange.topic." + topicId;
        Exchange exchange = ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
        amqpAdmin.declareExchange(exchange);
    }

    public void deleteTopicExchange(UUID topicId) {
        String exchangeName = "exchange.topic." + topicId;
        amqpAdmin.deleteExchange(exchangeName);
    }

    public void subscribeUserToTopic(UUID topicId, UUID userId) {
        String queueName = "queue.user." + userId;
        String exchangeName = "exchange.topic." + topicId;

        Queue queue = QueueBuilder.durable(queueName).build();
        TopicExchange exchange = new TopicExchange(exchangeName);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("");

        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }

    public void unsubscribeUserToTopic(UUID topicId, UUID userId) {
        String queueName = "queue.user." + userId;
        String exchangeName = "exchange.topic." + topicId;

        Queue queue = QueueBuilder.durable(queueName).build();
        TopicExchange exchange = new TopicExchange(exchangeName);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("");

        amqpAdmin.removeBinding(binding);
    }
}
