package com.abb.task22.service;

import com.abb.task22.domain.User;
import com.abb.task22.dto.UserRequest;
import com.abb.task22.exception.InsufficientBalanceException;
import com.abb.task22.exception.UserNotFoundException;
import com.abb.task22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setFullName("Test User");
        user.setBalance(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Save user - success")
    void save_shouldReturnUserId() {
        UserRequest request = new UserRequest();
        request.setFullName("New User");
        request.setBalance(new BigDecimal("200.00"));

        when(userRepository.save(any(User.class))).thenReturn(user);

        UUID result = userService.save(request);

        assertNotNull(result);
        assertEquals(userId, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Get user by ID - success")
    void getById_shouldReturnUser_whenUserExists() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Test User", result.getFullName());
    }

    @Test
    @DisplayName("Get user by ID - fails when user not found")
    void getById_shouldThrowException_whenUserNotFound() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
            userService.getById(userId)
        );
    }

    @Test
    @DisplayName("Decrease balance - success")
    void decreaseBalance_shouldSucceed_whenBalanceSufficient() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.decreaseBalance(userId, new BigDecimal("50.00"));

        verify(userRepository).decreaseBalance(userId, new BigDecimal("50.00"));
    }

    @Test
    @DisplayName("Decrease balance - fails when insufficient")
    void decreaseBalance_shouldFail_whenBalanceInsufficient() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        doThrow(new DataIntegrityViolationException("constraint violation"))
            .when(userRepository).decreaseBalance(userId, new BigDecimal("150.00"));

        assertThrows(InsufficientBalanceException.class, () ->
            userService.decreaseBalance(userId, new BigDecimal("150.00"))
        );
    }
}
