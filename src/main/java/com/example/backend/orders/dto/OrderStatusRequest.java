package com.example.backend.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusRequest {

    @NotBlank(message = "orderStatus không được để trống")
    @Pattern(
            regexp = "^(?i)(PENDING|PAID|COMPLETED|CANCELLED)$",
            message = "orderStatus chỉ được là PENDING, PAID, COMPLETED hoặc CANCELLED"
    )
    private String orderStatus;
}