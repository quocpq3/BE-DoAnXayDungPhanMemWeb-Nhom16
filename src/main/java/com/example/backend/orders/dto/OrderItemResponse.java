package com.example.backend.orders.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private Long orderItemId;
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}