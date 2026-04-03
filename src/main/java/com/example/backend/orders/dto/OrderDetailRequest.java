package com.example.backend.orders.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailRequest {

    @NotNull(message = "MENU_ITEM_ID_REQUIRED")
    private Long menuItemId;

    @NotNull(message = "QUANTITY_REQUIRED")
    @Min(value = 1, message = "QUANTITY_INVALID")
    private Integer quantity;

    @NotNull(message = "UNIT_PRICE_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "UNIT_PRICE_INVALID")
    private BigDecimal unitPrice;
}