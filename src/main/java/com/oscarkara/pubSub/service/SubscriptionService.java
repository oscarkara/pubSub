package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.model.Subscription;
import com.oscarkara.pubSub.model.Topic;
import com.oscarkara.pubSub.model.User;
import com.oscarkara.pubSub.repository.SubscriptionRepository;
import com.oscarkara.pubSub.repository.TopicRepository;
import com.oscarkara.pubSub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicSubscriptionService topicSubscriptionService;

    public void subscribe(UUID topicId, UUID userId){
        Optional<Subscription> existingSub = subscriptionRepository
                .findByTopicIdAndUserId(topicId, userId);
        if (existingSub.isPresent()) return;

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setUser(user);
        subscription.setSubscribedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);

        topicSubscriptionService.subscribeUserToTopic(topic.getId(), user.getId());
    }

    @Transactional
    public void unsubscribe(UUID topicId, UUID userId){
        subscriptionRepository.findByTopicIdAndUserId(topicId, userId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));

        subscriptionRepository.deleteByTopic_IdAndUser_Id(topicId, userId);

        topicSubscriptionService.unsubscribeUserToTopic(topicId, userId);
    }

}
