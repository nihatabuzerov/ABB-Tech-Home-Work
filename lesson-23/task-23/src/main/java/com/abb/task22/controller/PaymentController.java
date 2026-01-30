package com.abb.task22.controller;

import com.abb.task22.dto.PaymentDto;
import com.abb.task22.dto.PaymentRequest;
import com.abb.task22.dto.PaymentResponse;
import com.abb.task22.dto.UserPaymentDto;
import com.abb.task22.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    private final PaymentService paymentService;
    
    public PaymentController (PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @PostMapping
    public ResponseEntity<PaymentResponse>
    createPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
    
    @GetMapping
    public ResponseEntity<List<PaymentDto>>
    getAllPayments () {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
    
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto>
    getPaymentById (@PathVariable UUID paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPaymentDto>>
    getPaymentsByUserId (@PathVariable UUID userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }
}
