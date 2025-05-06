package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.model.User;
import com.oscarkara.pubSub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
