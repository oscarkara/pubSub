package com.oscarkara.pubSub.controller;

import com.oscarkara.pubSub.dto.TopicDTO;
import com.oscarkara.pubSub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @PostMapping
    public ResponseEntity<Void> publish(@RequestBody TopicDTO topicDTO){
        topicService.publishTopic(topicDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> delete(@PathVariable UUID topicId){
        topicService.deleteTopic(topicId);
        return ResponseEntity.ok().build();
    }

}
