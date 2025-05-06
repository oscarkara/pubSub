package com.oscarkara.pubSub.dto;

import java.util.UUID;

public record NewsDTO(UUID topicId, String title, String description) {
}
