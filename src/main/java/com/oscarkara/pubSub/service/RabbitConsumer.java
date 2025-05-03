package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.dto.NewsDTO;
import com.oscarkara.pubSub.model.Subscription;
import com.oscarkara.pubSub.repository.SubscriptionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = "#{queueNames}")
    public void receiveMessage(NewsDTO news) {
        System.out.println("Notícia recebida: " + news.title());
    }

    @Bean
    public List<String> queueNames(SubscriptionRepository subscriptionRepository) {
        List<String> queueNames = new ArrayList<>();
        for (Subscription sub : subscriptionRepository.findAll()) {
            String queue = "queue.user." + sub.getUser().getId();
            queueNames.add(queue);
        }
        return queueNames;
    }
}
