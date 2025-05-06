package com.oscarkara.pubSub.config;

import com.oscarkara.pubSub.repository.UserRepository;
import com.oscarkara.pubSub.security.UserLoginDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class ApplicationConfig {
    private final UserRepository userRepository;
    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usernameOrId -> {
            try {
                UUID userId = UUID.fromString(usernameOrId);
                return new UserLoginDetails(
                        userRepository.findById(userId)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pelo ID"))
                );
            } catch (IllegalArgumentException e) {
                return new UserLoginDetails(
                        userRepository.findByUsername(usernameOrId)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pelo username"))
                );
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
