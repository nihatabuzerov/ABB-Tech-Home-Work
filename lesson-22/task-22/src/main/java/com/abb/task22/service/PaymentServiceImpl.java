package com.abb.task22.service;

import com.abb.task22.domain.Payment;
import com.abb.task22.domain.PaymentStatus;
import com.abb.task22.domain.User;
import com.abb.task22.dto.PaymentDto;
import com.abb.task22.dto.PaymentRequest;
import com.abb.task22.dto.PaymentResponse;
import com.abb.task22.dto.UserPaymentDto;
import com.abb.task22.exception.PaymentNotFoundException;
import com.abb.task22.exception.UserNotFoundException;
import com.abb.task22.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            UserService userService
    ) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
       
        if (request == null) {
            throw new IllegalArgumentException("Payment request cannot be null");
        }

        if (!userService.existsById(request.getUserId())) {
            throw new UserNotFoundException();
        }

        Payment payment = new Payment();
        
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        UUID paymentId = paymentRepository.save(payment);

        userService.decreaseBalance(request.getUserId(), request.getAmount());

        paymentRepository.updateStatus(paymentId, PaymentStatus.SUCCESS);

        User user = userService.getById(request.getUserId());

        return PaymentResponse
                .builder()
                .paymentId(paymentId)
                .status(PaymentStatus.SUCCESS.name())
                .balance(user.getBalance())
                .build();
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository
                .findAll()
                .stream()
                .map(this::toPaymentDto)
                .toList();
    }

    @Override
    public PaymentDto getPaymentById(UUID id) {
        Payment payment = paymentRepository
                .findById(id)
                .orElseThrow(PaymentNotFoundException::new);
      
        return toPaymentDto(payment);
    }

    @Override
    public List<UserPaymentDto> getPaymentsByUserId(UUID userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        
        return paymentRepository
                .findByUserId(userId)
                .stream()
                .map(this::toUserPaymentDto)
                .toList();
    }

    private PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto
                .builder()
                .paymentId(payment.getId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    private UserPaymentDto toUserPaymentDto(Payment payment) {
        return UserPaymentDto
                .builder()
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
