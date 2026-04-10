package com.example.backend.payment.service;

import com.example.backend.payment.dto.PaymentRequest;
import com.example.backend.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse completePayment(PaymentRequest request);
}