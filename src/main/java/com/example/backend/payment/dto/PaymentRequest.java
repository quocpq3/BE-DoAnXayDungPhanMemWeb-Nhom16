package com.example.backend.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    Long orderId;       // ID của bảng order_detail
    String transactionId;
    String paymentMethod;      // VNPAY, MOMO, CASH
}