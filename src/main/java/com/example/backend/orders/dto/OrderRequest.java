package com.example.backend.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long userId;

    @NotBlank(message = "CUSTOMER_NAME_BLANK")
    @Size(max = 100, message = "CUSTOMER_NAME_INVALID")
    private String customerName;

    @Size(max = 20, message = "CUSTOMER_PHONE_INVALID")
    private String customerPhone;

    @Size(max = 255, message = "DELIVERY_ADDRESS_INVALID")
    private String deliveryAddress;

    private String orderStatus;
    private String paymentMethod;
    private String deliveryMethod;
    private String note;

    @Valid
    private List<OrderItemRequest> items;
}