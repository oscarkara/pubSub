package com.oscarkara.pubSub.dto;

import java.util.UUID;

public record RabbitNewsDTO(UUID id, String title, String description, UUID creatorId, String creatorUsername, UUID topicId, String topicName) { }
