package com.example.backend.orders.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private String orderCode;

    private Long userId;
    private String userName;

    private String customerPhone;
    private String deliveryAddress;
    private String orderStatus;
    private String paymentMethod;
    private String deliveryMethod;
    private BigDecimal totalAmount;
    private String note;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}