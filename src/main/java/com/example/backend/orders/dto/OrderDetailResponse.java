package com.example.backend.orders.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {

    private Long orderDetailId;
    private Long menuItemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}