package com.example.backend.combodetail.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ComboDetailRequest {
    @NotNull(message = "COMBO_ID_NULL")
    private Long comboItemId;

    @NotNull(message = "COMPONENT_ID_NULL")
    private Long componentItemId;

    @Min(value = 1, message = "QUANTITY_INVALID")
    private Integer quantity;
}