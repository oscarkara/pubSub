package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.dto.RabbitNewsDTO;
import com.oscarkara.pubSub.model.News;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(News news) {
        RabbitNewsDTO newsDTO = new RabbitNewsDTO(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getCreator().getId(),
                news.getCreator().getUsername(),
                news.getTopic().getId(),
                news.getTopic().getName()
        );

        String exchangeName = "exchange.topic." + news.getTopic().getId();
        rabbitTemplate.convertAndSend(exchangeName, "", newsDTO);
    }
}
