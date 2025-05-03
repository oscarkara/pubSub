package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.config.RabbitMQConfig;
import com.oscarkara.pubSub.dto.NewsDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishNews(String topicName, NewsDTO news) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_PREFIX + topicName,
                news
        );
    }
}
