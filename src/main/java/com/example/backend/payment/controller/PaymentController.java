package com.example.backend.payment.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.payment.dto.PaymentRequest;
import com.example.backend.payment.dto.PaymentResponse;
import com.example.backend.payment.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @PostMapping("/complete")
    public ApiResponse<PaymentResponse> complete(@RequestBody PaymentRequest request) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.completePayment(request))
                .build();
    }

}