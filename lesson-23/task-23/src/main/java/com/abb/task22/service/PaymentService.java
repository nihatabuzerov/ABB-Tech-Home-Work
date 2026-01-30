package com.abb.task22.service;

import com.abb.task22.dto.PaymentDto;
import com.abb.task22.dto.PaymentRequest;
import com.abb.task22.dto.PaymentResponse;
import com.abb.task22.dto.UserPaymentDto;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    List<PaymentDto> getAllPayments();

    PaymentDto getPaymentById(UUID id);

    List<UserPaymentDto> getPaymentsByUserId(UUID userId);
}
