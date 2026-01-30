package com.abb.task22.service;

import com.abb.task22.domain.User;
import com.abb.task22.dto.UserRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface UserService {

    User getById(UUID id);

    List<User> getAll();

    boolean existsById(UUID id);

    UUID save(UserRequest request);

    void decreaseBalance(UUID userId, BigDecimal amount);

    void increaseBalance(UUID userId, BigDecimal amount);
}
