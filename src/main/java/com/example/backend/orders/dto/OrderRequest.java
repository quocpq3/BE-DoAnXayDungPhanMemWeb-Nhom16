package com.example.backend.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotBlank(message = "INVALID_KEY")
    private String customerName;

    private String customerPhone;
    private String deliveryAddress;

    @Pattern(
            regexp = "^(?i)(PENDING|PAID|COMPLETED|CANCELLED)$",
            message = "INVALID_KEY"
    )
    private String orderStatus;

    private String paymentMethod;
    private String deliveryMethod;
    private String note;

    @Valid
    @NotEmpty(message = "INVALID_KEY")
    private List<OrderItemRequest> items;
}