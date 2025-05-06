package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("queueProvider")
public class QueueProvider {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<String> getUserQueues() {
        return subscriptionRepository.findAll().stream()
                .map(sub -> "queue.user." + sub.getUser().getId())
                .toList();
    }
}
