package com.oscarkara.pubSub.controller;

import com.oscarkara.pubSub.security.UserLoginDetails;
import com.oscarkara.pubSub.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    @PostMapping("/{topicId}")
    public ResponseEntity<?> subscribe(@PathVariable UUID topicId, @AuthenticationPrincipal  UserLoginDetails userLoginDetails){
        subscriptionService.subscribe(topicId, userLoginDetails.user().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable UUID topicId,
                                            @AuthenticationPrincipal UserLoginDetails loginDetails) {
        subscriptionService.unsubscribe(topicId, loginDetails.user().getId());
        return ResponseEntity.noContent().build();
    }

}
