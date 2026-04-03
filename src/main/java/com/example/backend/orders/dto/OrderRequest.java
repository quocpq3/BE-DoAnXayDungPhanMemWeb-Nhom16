package com.example.backend.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @Size(max = 100, message = "CUSTOMER_NAME_INVALID")
    private String customerName;

    @Size(max = 20, message = "CUSTOMER_PHONE_INVALID")
    private String customerPhone;

    private String note;

    @Valid
    private List<OrderDetailRequest> details;
}