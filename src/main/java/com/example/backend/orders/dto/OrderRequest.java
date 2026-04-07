package com.example.backend.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long userId;

    @NotBlank(message = "customerName không được để trống")
    private String customerName;

    private String customerPhone;
    private String deliveryAddress;
    private String paymentMethod;
    private String deliveryMethod;
    private String note;

    @Valid
    @NotEmpty(message = "Danh sách món không được rỗng")
    private List<OrderItemRequest> items;
}