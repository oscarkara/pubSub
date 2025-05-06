package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.model.User;
import com.oscarkara.pubSub.repository.UserRepository;
import com.oscarkara.pubSub.security.UserLoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uuidString) throws UsernameNotFoundException {
        UUID uuid = UUID.fromString(uuidString);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        return new UserLoginDetails(user);
    }
}
