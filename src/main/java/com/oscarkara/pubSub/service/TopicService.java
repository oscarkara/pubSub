package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.dto.TopicDTO;
import com.oscarkara.pubSub.model.Subscription;
import com.oscarkara.pubSub.model.Topic;
import com.oscarkara.pubSub.repository.SubscriptionRepository;
import com.oscarkara.pubSub.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private TopicSubscriptionService topicSubscriptionService;
    public void publishTopic(TopicDTO topicDTO) {
        Topic topic = new Topic();
        topic.setName(topicDTO.name());
        topic = topicRepository.save(topic);

        topicSubscriptionService.createTopicExchange(topic.getId());
    }

    public void deleteTopic(UUID topicId){
        topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        var subscriptionList = subscriptionRepository.findAllByTopicId(topicId);

        for(Subscription sub : subscriptionList){
            topicSubscriptionService.unsubscribeUserToTopic(topicId, sub.getUser().getId());
        }

        topicSubscriptionService.deleteTopicExchange(topicId);
    }
}
