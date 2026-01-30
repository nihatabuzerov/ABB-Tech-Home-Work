package com.abb.task22.service;

import com.abb.task22.domain.Payment;
import com.abb.task22.domain.PaymentStatus;
import com.abb.task22.domain.User;
import com.abb.task22.dto.PaymentRequest;
import com.abb.task22.dto.PaymentResponse;
import com.abb.task22.exception.InsufficientBalanceException;
import com.abb.task22.exception.UserNotFoundException;
import com.abb.task22.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private UUID userId;
    private UUID paymentId;
    private PaymentRequest paymentRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        paymentId = UUID.randomUUID();

        paymentRequest = new PaymentRequest();
        paymentRequest.setUserId(userId);
        paymentRequest.setAmount(new BigDecimal("50.00"));

        user = new User();
        user.setId(userId);
        user.setFullName("Test User");
        user.setBalance(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Create payment - success when balance is sufficient")
    void createPayment_shouldSucceed_whenBalanceIsSufficient() {
        
        when(userService.existsById(userId)).thenReturn(true);
        
        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentId);
        
        when(userService.getById(userId)).thenReturn(user);

        PaymentResponse response = paymentService.createPayment(paymentRequest);

        assertNotNull(response);
        assertEquals(paymentId, response.getPaymentId());
        assertEquals("SUCCESS", response.getStatus());

        verify(paymentRepository).save(any(Payment.class));
        verify(userService).decreaseBalance(userId, new BigDecimal("50.00"));
        verify(paymentRepository).updateStatus(paymentId, PaymentStatus.SUCCESS);
    }

    @Test
    @DisplayName("Create payment - fails when user not found")
    void createPayment_shouldFail_whenUserNotFound() {
       
        when(userService.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
            paymentService.createPayment(paymentRequest)
        );

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    @DisplayName("Create payment - fails when balance is insufficient")
    void createPayment_shouldFail_whenBalanceInsufficient() {
       
        when(userService.existsById(userId)).thenReturn(true);
       
        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentId);
       
        doThrow(new InsufficientBalanceException())
            .when(userService).decreaseBalance(userId, new BigDecimal("50.00"));

        assertThrows(InsufficientBalanceException.class, () ->
            paymentService.createPayment(paymentRequest)
        );

        verify(paymentRepository, never()).updateStatus(any(), any());
    }
}
