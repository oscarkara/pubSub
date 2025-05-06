package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.dto.NewsDTO;
import com.oscarkara.pubSub.model.News;
import com.oscarkara.pubSub.model.Topic;
import com.oscarkara.pubSub.model.User;
import com.oscarkara.pubSub.repository.NewsRepository;
import com.oscarkara.pubSub.repository.SubscriptionRepository;
import com.oscarkara.pubSub.repository.TopicRepository;
import com.oscarkara.pubSub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NewsPublisher newsPublisher;

    @Transactional
    public void publish(UUID userId, NewsDTO newsDTO){
        Topic topic = topicRepository.findById(newsDTO.topicId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        subscriptionRepository.findByTopicIdAndUserId(newsDTO.topicId(), userId)
                .orElseThrow(() -> new RuntimeException("Usuário não inscrito"));;

        News news = new News();
        news.setCreator(user);
        news.setTopic(topic);
        news.setTitle(newsDTO.title());
        news.setDescription(newsDTO.description());
        news.setCreatedAt(LocalDateTime.now());
        newsRepository.save(news);

        newsPublisher.publish(news);
    }

}
