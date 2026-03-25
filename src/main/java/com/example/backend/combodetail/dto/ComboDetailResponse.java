package com.example.backend.combodetail.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ComboDetailResponse {
    private Long comboItemId;
    private Long componentItemId;
    private String componentName;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal; // quantity * unitPrice
}