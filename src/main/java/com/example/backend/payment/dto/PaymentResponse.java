package com.example.backend.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Long paymentId;
    Long orderId;
    String transactionId;
    String paymentMethod;
    BigDecimal amount;
    String status;
    LocalDateTime paymentDate;
}