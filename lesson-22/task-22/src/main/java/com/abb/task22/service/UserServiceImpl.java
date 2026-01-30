package com.abb.task22.service;

import com.abb.task22.domain.User;
import com.abb.task22.dto.UserRequest;
import com.abb.task22.exception.InsufficientBalanceException;
import com.abb.task22.exception.UserNotFoundException;
import com.abb.task22.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID save(UserRequest request) {
      
        if (request == null) {
            throw new IllegalArgumentException("User request cannot be null");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setBalance(request.getBalance());

        return userRepository.save(user);
    }
    
    @Override
    public User getById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }
    
    @Override
    public void decreaseBalance(UUID userId, BigDecimal amount) {
        getById(userId);

        try {
            userRepository.decreaseBalance(userId, amount);
        } catch (DataIntegrityViolationException e) {
            throw new InsufficientBalanceException();
        }
    }

    @Override
    public void increaseBalance(UUID userId, BigDecimal amount) {
        getById(userId);
        userRepository.increaseBalance(userId, amount);
    }
}
