package com.oscarkara.pubSub.controller;

import com.oscarkara.pubSub.dto.NewsDTO;
import com.oscarkara.pubSub.security.UserLoginDetails;
import com.oscarkara.pubSub.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/topic/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @PreAuthorize("hasRole('CREATOR')")
    @PostMapping
    public ResponseEntity<Void> publish(@RequestBody NewsDTO newsDTO, @AuthenticationPrincipal  UserLoginDetails userLoginDetails){
        newsService.publish(userLoginDetails.user().getId(), newsDTO);
        return ResponseEntity.ok().build();
    }

}
