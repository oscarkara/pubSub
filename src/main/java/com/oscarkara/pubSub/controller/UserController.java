package com.oscarkara.pubSub.controller;

import com.oscarkara.pubSub.model.User;
import com.oscarkara.pubSub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

}
