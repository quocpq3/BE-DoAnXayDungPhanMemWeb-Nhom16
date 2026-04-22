package com.example.backend.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "userId không được để trống")
    private Long userId;

    private String deliveryAddress;

    @Pattern(
            regexp = "^(?i)(PENDING|PAID|COMPLETED|CANCELLED)$",
            message = "orderStatus chỉ được là PENDING, PAID, COMPLETED hoặc CANCELLED"
    )
    private String orderStatus;

    private String paymentMethod;
    private String deliveryMethod;
    private String note;

    @Valid
    @NotEmpty(message = "Danh sách món không được rỗng")
    private List<OrderItemRequest> items;
}