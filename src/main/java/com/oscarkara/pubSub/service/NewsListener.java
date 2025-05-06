package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.dto.RabbitNewsDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewsListener {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "#{queueProvider.getUserQueues()}")
    public void receiveNews(RabbitNewsDTO news) {
        messagingTemplate.convertAndSend("/topic/news/" + news.topicId(), news);
    }

}
