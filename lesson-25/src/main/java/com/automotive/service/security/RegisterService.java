package com.automotive.service.security;

import com.automotive.dto.security.RegisterRequestDto;
import com.automotive.exception.AuthErrorEnum;
import com.automotive.exception.AuthException;
import com.automotive.model.AppUser;
import com.automotive.model.Role;
import com.automotive.repository.security.RoleRepository;
import com.automotive.repository.security.UserRepository;
import com.automotive.repository.security.RoleRepository;
import com.automotive.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final com.automotive.repository.security.UserRepository userRepo;
    private final com.automotive.repository.security.RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    @Transactional
    public void register(RegisterRequestDto req) {

        if (userRepo.findByUsername(req.username()).isPresent()) {
            throw new AuthException(AuthErrorEnum.USERNAME_EXISTS);
        }

        Role userRole = roleRepo.findByName("USER").orElseThrow();

        AppUser user = new AppUser();
        user.setUsername(req.username());
        user.setFullName(req.fullName());
        user.setEmail(req.email());

        user.setPasswordHash(encoder.encode(req.password()));
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(Set.of(userRole));
        userRepo.save(user);
    }
}


