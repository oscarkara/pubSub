package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.model.Role;
import com.oscarkara.pubSub.model.User;
import com.oscarkara.pubSub.repository.UserRepository;
import com.oscarkara.pubSub.security.AuthRequest;
import com.oscarkara.pubSub.security.AuthResponse;
import com.oscarkara.pubSub.security.RegisterRequest;
import com.oscarkara.pubSub.security.UserLoginDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(Role.USER);

        userRepository.save(user);

        UserLoginDetails userDetails = new UserLoginDetails(user);
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", "1Hour");
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        UserLoginDetails userDetails = new UserLoginDetails(user);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, "Bearer", "1Hour");
    }
}
