package com.oscarkara.pubSub.repository;

import com.oscarkara.pubSub.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findAllByTopicId(UUID topicId);
    Optional<Subscription> findByTopicIdAndUserId(UUID topicId, UUID userId);

    void deleteByTopic_IdAndUser_Id(UUID topicId, UUID userId);

}
