package com.oscarkara.pubSub.repository;

import com.oscarkara.pubSub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
}