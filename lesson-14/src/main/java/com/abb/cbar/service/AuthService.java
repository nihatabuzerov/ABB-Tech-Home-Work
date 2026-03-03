package com.abb.cbar.service;

import com.abb.cbar.dto.AuthResponse;
import com.abb.cbar.dto.LoginRequest;
import com.abb.cbar.dto.RegisterRequest;
import com.abb.cbar.entity.User;
import com.abb.cbar.repository.UserRepository;
import com.abb.cbar.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register (RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        var user = new User(request.username(), passwordEncoder.encode(request.password()));
        userRepository.save(user);
        
        return new AuthResponse(jwtTokenProvider.generateToken(user.getUsername()), user.getUsername());
    }
    
    public AuthResponse login (LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        }
        
        catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        
        return new AuthResponse(jwtTokenProvider.generateToken(request.username()), request.username());
    }
}
